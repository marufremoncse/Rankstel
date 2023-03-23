package com.codingsense.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codingsense.model.ApiPayload;
import com.codingsense.model.RequestPayload;
import com.codingsense.model.ResponsePayload;
import com.codingsense.model.Route;
import com.codingsense.model.Status;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(path = "/api/v2")
public class ApiController {

	@Autowired
	Route route;

	@Autowired
	ApiPayload apiPayload;

	@PostMapping({ "/promo/sendsms", "/trans/sendsms" })

	public HashMap<String, Object> callProcess(@RequestBody RequestPayload requestPayload) {
		System.out.println("REQUEST_PAYLOAD: " + requestPayload);

		HashMap<String, Object> main = new HashMap<>();

		apiPayload.setUsername(requestPayload.getUsername());
		apiPayload.setPassword(requestPayload.getPassword());
		apiPayload.setDataCoding(requestPayload.getDataCoding());
		apiPayload.setGsm(requestPayload.getMsisdn().split(","));
		apiPayload.setSender(requestPayload.getCli());
		apiPayload.setText(requestPayload.getMessage());
		apiPayload.setType(requestPayload.getType());
		apiPayload.setValidityPeriod("48:00");

		System.out.println("API_PAYLOAD: " + apiPayload.toString());

		HashMap<String, String> auth = new HashMap<>();
		auth.put("username", apiPayload.getUsername());
		auth.put("password", apiPayload.getPassword());

		main.put("authentication", auth);

		HashMap<String, Object> msg = new HashMap<>();
		msg.put("sender", apiPayload.getSender());

		System.out.println("requestPayload.getIsLongSMS(): " + requestPayload.isIslongSMS());
		if (requestPayload.isIslongSMS() == true) {
			msg.put("type", "longSMS");
//			apiPayload.setText(requestPayload.getLongSMS());
		}
		System.out.println("I am here: " + requestPayload.isUnicode());
		if (requestPayload.isUnicode() == true) {
			msg.put("datacoding", "8");

//			apiPayload.setText(requestPayload.getUnicode());
		}
		if (requestPayload.isFlash() == true) {
//			apiPayload.setText(requestPayload.getFlash());
		}
		msg.put("text", apiPayload.getText());
		ArrayList<Object> ls = new ArrayList<>();
		int i = 0;
		HashMap<String, String[]> responseMsisdn = new HashMap<>();
		for (String msisdn : apiPayload.getGsm()) {
			HashMap<String, String> gsm = new HashMap<>();
			gsm.put("gsm", "88" + msisdn.trim());
			String messageId = String.valueOf(apiPayload.getId() + i++);
			gsm.put("messageId", messageId);
			ls.add(gsm);
		}

		msg.put("recipients", ls);

		ArrayList<Object> msgArr = new ArrayList<>();
		msgArr.add(msg);

		main.put("messages", msgArr);

		ResponsePayload responsePayload = new ResponsePayload();
		responsePayload.setClienttransid(requestPayload.getClienttransid());
		responsePayload = process(apiPayload, responsePayload, main, route);

		HashMap<String, Object> responseMap = new HashMap<>();
		HashMap<String, Object> statusInfo = new HashMap<>();

		statusInfo.put("statusCode", responsePayload.getStatusCode());
		statusInfo.put("errordescription", responsePayload.getErrordescription());
		statusInfo.put("clienttransid", responsePayload.getClienttransid());
		statusInfo.put("messageIDs", responsePayload.getMessageIDs());
		responseMap.put("statusInfo", statusInfo);
		return responseMap;
	}

	public ResponsePayload process(ApiPayload apiPayload, ResponsePayload responsePayload,
			HashMap<String, Object> jsonInput, Route route) {
		System.out.println("REQ_ID:" + apiPayload.getId());

		Status st = new Status();
		st.setStatus("");
		String SMS_RESPONSE = "";
		String status = "";
		long tm = System.currentTimeMillis();
		try {
			String urlString = route.getApiRoot() + "/api/v3/sendsms/json";
			URL url = new URL(urlString);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);
			con.setConnectTimeout(20000);
			con.setReadTimeout(20000);

			var objectMapper = new ObjectMapper();
			String jsonInputString = objectMapper.writeValueAsString(jsonInput);

			System.out.println("PAYLOAD_JSON: " + jsonInputString);

			try (OutputStream os = con.getOutputStream()) {
				byte[] input = jsonInputString.getBytes("utf-8");
				os.write(input, 0, input.length);
			}
			try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
				StringBuilder response = new StringBuilder();
				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
				SMS_RESPONSE = response.toString();
				System.out.println("RESPONSE: " + SMS_RESPONSE);
			}

			JSONParser j = new JSONParser();
			JSONObject o = (JSONObject) j.parse(SMS_RESPONSE);
			JSONArray jsonArray = (JSONArray) o.get("results");

			Iterator<JSONObject> iterator = jsonArray.iterator();

			HashMap<String, String> responseMsisdn = new HashMap<>();
			while (iterator.hasNext()) {
				JSONObject r = iterator.next();

				status = r.get("status").toString();
				String destination = r.get("destination").toString();
				String messageid = r.get("messageid").toString();

				responseMsisdn.put(destination.substring(2), messageid);
			}
			responsePayload.setMessageIDs(responseMsisdn);

			switch (status) {
			case "0":
				responsePayload.setStatusCode("1000");
				responsePayload.setErrordescription("Success");
				break;
			case "-1":
				responsePayload.setStatusCode("1014");
				responsePayload.setErrordescription("Delivery pending");
				break;
			case "-5":
				responsePayload.setStatusCode("1002");
				responsePayload.setErrordescription("Invalid Username");
				break;
			default:
				responsePayload.setStatusCode("1020");
				responsePayload.setErrordescription("Internal Server Error");
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			responsePayload.setStatusCode("1020");
			responsePayload.setErrordescription("Internal Server Error");
			responsePayload.setMessageIDs(null);
		}

		long tmt = System.currentTimeMillis();

		st.setTrid(String.valueOf(apiPayload.getId()));
		st.setStatus(status);
		st.setTt(tmt - tm);

		return responsePayload;
	}

	@GetMapping("/balance")
	public String balanceCheck(@RequestBody RequestPayload requestPayload) {
		System.out.println("REQUEST_PAYLOAD: " + requestPayload);

		String encodeFormat = "UTF-8";
		String response = "";
		try {
			String user = URLEncoder.encode("user", encodeFormat) + "="
					+ URLEncoder.encode(requestPayload.getUsername(), encodeFormat);
			String password = URLEncoder.encode("password", encodeFormat) + "="
					+ URLEncoder.encode(requestPayload.getPassword(), encodeFormat);
			String cmd = URLEncoder.encode("cmd", encodeFormat) + "=" + URLEncoder.encode("CREDITS", encodeFormat);

			String urlString = route.getApiRoot() + "/api/command?" + user + "&" + password + "&" + cmd;
			System.out.println("url: " + urlString);
			URL url = new URL(urlString);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setConnectTimeout(20000);
			con.setReadTimeout(20000);
			con.setRequestMethod("GET");

			int responseCode = con.getResponseCode();
			System.out.println("GET Response Code :: " + responseCode);
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer responseBuffer = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					responseBuffer.append(inputLine);
				}
				in.close();

				// print result
				response = responseBuffer.toString();
				System.out.println("response: " + response);
			} else {
				System.out.println("GET request did not work.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
}