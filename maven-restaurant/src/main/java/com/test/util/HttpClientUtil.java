package com.test.util;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.*;
import java.security.cert.CertificateException;

public class HttpClientUtil {

    public static String KEY_STORE_FILE="imp_client.p12";
    public static String KEY_STORE_PASS="nymeria";
    public static String TRUST_STORE_FILE="cvte.com.jks";
    public static String TRUST_STORE_PASS="123456";

    private static SSLContext sslContext;

    /**
     * Post Request
     * @return
     * @throws Exception
     */
    public static String doPost(String url, byte[] data, String contentType) throws Exception {
    	System.out.println(url);
        URL localURL = new URL(url);
        HttpURLConnection conn = (HttpURLConnection)localURL.openConnection();
        
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", contentType);

        DataOutputStream os = new DataOutputStream(conn.getOutputStream());
        os.write(data);
        os.flush();
        os.close();
        conn.connect();
        
        InputStream inputStream = null;
        
        try {
            
            if (conn.getResponseCode() >= 300) {
                throw new Exception("HTTP Request is not success, Response code is " + conn.getResponseCode());
            }
            inputStream = conn.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));  
            StringBuffer buffer = new StringBuffer();  
            String line = "";  
            while ((line = in.readLine()) != null){ 
            	buffer.append(line);  
            } 
            
            return buffer.toString();      
        } catch (Exception e) {
			throw e;
		} finally {
            if (os != null) {
            	os.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            
        }
    }

    /**
     * 上传文件  https验证
     * @param url
     * @param file
     * @param contentType
     * @return
     * @throws Exception
     */
    public static String uploadFile(String url, File file, String contentType)throws Exception{

        HttpURLConnection conn = null;
        OutputStream out = null;
        DataInputStream in = null;
        InputStream ins = null;
        ByteArrayOutputStream outStream = null;
        try{
            URL localURL = new URL(url);
            conn = (HttpURLConnection)localURL.openConnection();
            if(conn instanceof HttpsURLConnection){
                ((HttpsURLConnection)conn).setSSLSocketFactory(getSSLContext().getSocketFactory());
            }
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", contentType);
            conn.connect();

            out = conn.getOutputStream();
            in = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] buffer = new byte[1024];
            while((bytes = in.read(buffer)) != -1){
                out.write(buffer,0,bytes);
            }
            out.flush();
            StringBuffer stringBuffer = null;
            //返回数据
            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                ins = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
                stringBuffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null){
                    stringBuffer.append(line);
                }
            }
            return stringBuffer.toString();
        }catch (Exception e){
            throw e;
        }finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (ins != null) {
                ins.close();
            }
            if (outStream != null) {
                outStream.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public static SSLContext getSSLContext(){
        long time1=System.currentTimeMillis();
        if(sslContext==null){
            try {
                KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
                kmf.init(getkeyStore(),KEY_STORE_PASS.toCharArray());
                KeyManager[] keyManagers = kmf.getKeyManagers();

                TrustManagerFactory trustManagerFactory=TrustManagerFactory.getInstance("SunX509");
                trustManagerFactory.init(getTrustStore());
                TrustManager[]  trustManagers= trustManagerFactory.getTrustManagers();

                sslContext = SSLContext.getInstance("TLS");
                sslContext.init(keyManagers, trustManagers, new SecureRandom());
                HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnrecoverableKeyException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
        }
        long time2=System.currentTimeMillis();
        System.out.println("SSLContext 初始化时间："+(time2-time1));
        return sslContext;
    }


    public static KeyStore getkeyStore(){
        KeyStore keySotre=null;
        try {
            keySotre = KeyStore.getInstance("PKCS12");
            FileInputStream fis = new FileInputStream(new File(KEY_STORE_FILE));
            keySotre.load(fis, KEY_STORE_PASS.toCharArray());
            fis.close();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return keySotre;
    }
    public static KeyStore getTrustStore() throws IOException{
        KeyStore trustKeyStore=null;
        FileInputStream fis=null;
        try {
            trustKeyStore=KeyStore.getInstance("JKS");
            fis = new FileInputStream(new File(TRUST_STORE_FILE));
            trustKeyStore.load(fis, TRUST_STORE_PASS.toCharArray());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            fis.close();
        }
        return trustKeyStore;
    }
}
