package me.univ.flex.common.service.email.thunder;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.properties.FlexProperties;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ThunderMailAPI {

  private final ObjectMapper objectMapper;
  private final FlexProperties flexProperties;

  public ThunderMailResponse sendEmail(ThunderMailRequest thunderMailRequest) {
    OutputStreamWriter out = null;
    BufferedReader in = null;
    String strReadLine = "";
    ThunderMailResponse thunderMailResponse = null;
    try {

      String payload = objectMapper.writeValueAsString(thunderMailRequest);

      URL url = new URL(flexProperties.getEmailProps().getApiUrl());
      URLConnection connection = url.openConnection();
      HttpURLConnection hurlc = (HttpURLConnection) connection;
      hurlc.setRequestProperty("content-Type", "application/json");
      hurlc.setRequestMethod("POST");
      hurlc.setDoOutput(true);
      hurlc.setDoInput(true);
      hurlc.setUseCaches(false);
      hurlc.setDefaultUseCaches(false);
      hurlc.connect();
      out = new OutputStreamWriter(hurlc.getOutputStream(), "UTF-8");
      out.write(payload);
      out.flush();
      out.close();
      in = new BufferedReader(new InputStreamReader(hurlc.getInputStream(), "UTF-8"));

      for (String strInLine = ""; (strInLine = in.readLine()) != null;
          strReadLine = strReadLine + strInLine) {
      }

      in.close();

      thunderMailResponse = objectMapper.readValue(strReadLine, ThunderMailResponse.class);

      // log.debug(payload);
      log.debug(strReadLine);

    } catch (Exception e) {
      log.error(e.toString());
    } finally {
      try {
        if (out != null) {
          out.close();
        }
        if (in != null) {
          in.close();
        }
      } catch (Exception e) {
        log.error(e.toString());
      }
    }

    return thunderMailResponse == null ? new ThunderMailResponse() : thunderMailResponse;
  }
}
