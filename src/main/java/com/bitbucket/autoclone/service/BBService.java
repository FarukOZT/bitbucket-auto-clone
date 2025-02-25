package com.bitbucket.autoclone.service;

import com.bitbucket.autoclone.model.BBModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class BBService {

    @Value("${url}")
    String url;
    @Autowired
    ObjectMapper objectMapper;
    private final OkHttpService okHttpService;

    public BBService(OkHttpService okHttpService) {
        this.okHttpService = okHttpService;
    }

    //kullanici adi ve sifre
    String encodedUserName = Base64.getEncoder().encodeToString(("yourusername" + ":" + "yourpassword").getBytes());
    //String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
    byte[] decodedBytes = Base64.getDecoder().decode(encodedUserName);
    String authHeader = "Basic " + encodedUserName;

    public List<String> requestBB2() throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

        OkHttpClient.Builder newBuilder = new OkHttpClient.Builder();
        newBuilder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);
        newBuilder.hostnameVerifier((hostname, session) -> true);

        Request bbcall = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", authHeader)
                .build();
        ResponseBody body = newBuilder.build().newCall(bbcall).execute().body();
        String resp = body.string();

        BBModel bbModel = objectMapper.readValue(resp, BBModel.class);
        List<String> bbModelList = new ArrayList<>();
        for (int i = 0; i < bbModel.getValues().size(); i++) {
            if (bbModel.getValues().get(i).getLinks().getClone().get(0).getName().equalsIgnoreCase("http")) {
                bbModelList.add(bbModel.getValues().get(i).getLinks().getClone().get(0).getHref());
            } else if (bbModel.getValues().get(i).getLinks().getClone().get(1).getName().equalsIgnoreCase("http")) {
                bbModelList.add(bbModel.getValues().get(i).getLinks().getClone().get(1).getHref());
            }
        }
        return bbModelList;
    }

    public void gitClone() throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        File localRepo = new File("C:\\Users\\ozturkaf\\nextdeneme");
        List<String> addressList = new ArrayList<>(requestBB2());
        for (int i = 0; i < addressList.size(); i++) {
            String gitCommand = "git clone " + addressList.get(i);
            List<String> command = new ArrayList<>();
            command.add("cmd.exe");  // Windows'ta CMD'yi calistiriyoruz korkacak bisey yok
            command.add("/C");
            command.add("cd " + localRepo);
            command.add(gitCommand);
            ProcessBuilder processBuilder = new ProcessBuilder();

            processBuilder.command("C:/Program Files/Git/git-bash.exe", "-c", gitCommand);


            processBuilder.directory(localRepo);
            System.out.println(processBuilder.directory());

            processBuilder.redirectErrorStream(true);

            try {
                Process process = processBuilder.start();

                InputStream inputStream = process.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

                int exitCode = process.waitFor();
                System.out.println("Exit Code: " + exitCode);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /*private static HttpComponentsClientHttpRequestFactory createRequestFactory() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContext.getDefault();

        SSLConnectionSocketFactory sslConnectionFactory = new SSLConnectionSocketFactory(
                sslContext,
                new String[]{"TLSv1.2"}, // important
                null,
                NoopHostnameVerifier.INSTANCE);

        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", sslConnectionFactory)
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .build();

        HttpClientConnectionManager ccm = new BasicHttpClientConnectionManager(registry);
        HttpClient httpclient = HttpClientBuilder.create().setSSLSocketFactory(sslConnectionFactory)
                .setConnectionManager(ccm)
                .build();
    }
*/
}
