package br.usp.each.saeg.code.forest.send.data;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class ScpSend {
	
	private String FILENAME = System.getProperty("user.home") + System.getProperty("file.separator")+"Desktop"+System.getProperty("file.separator")+"code_forest_commands.log";
	private String USER = "hamario";
	private String HOST = "ime.usp.br";
	private String REMOTEFILE = "logfiles/codeforest_";	
	//to test scp locally
	//private String USER = "";
	//private String HOST = "";
	//private String REMOTEFILE = "C:\\teste";
	private FileInputStream inputStream;
	
	public void sendFile(){
		
		System.out.println("Nome do arquivo de log:"+FILENAME);
		
		JSch jsch = new JSch();
		
		try {
			Session session = jsch.getSession(USER, HOST, 22);
			session.setPassword("gu@rose1");//set your password here
			
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			
			session.setConfig(config);
			session.connect();
			
			String command = "scp -t " + REMOTEFILE + System.currentTimeMillis() + ".log";
			System.out.println(command);
			Channel channel = session.openChannel("exec");
			((ChannelExec)channel).setCommand(command);
			
			OutputStream out = channel.getOutputStream();
			InputStream in = channel.getInputStream();
			
			channel.connect();
			
			if(checkAck(in)!=0)
				System.out.println("Erro no envio de dados");
			
			File logFile = new File(FILENAME);
			
			long filesize = logFile.length();
			command="C0644 " + filesize + " ";
			if(FILENAME.lastIndexOf('/')>0){
				command+=FILENAME.substring(FILENAME.lastIndexOf('/')+1);
			}else{
				command+=FILENAME;
			}
			command+="\n";
			
			out.write(command.getBytes());
			out.flush();
			
			if(checkAck(in)!=0)
				System.out.println("Errro no envio de dados");
			
			inputStream = new FileInputStream(FILENAME);
			byte[] buffer = new byte[1024];
			
			while(true){
				int length = inputStream.read(buffer, 0, buffer.length);
				if(length <= 0){
					break;
				}
				out.write(buffer,0,length);
				System.out.println(new String(buffer,0,length));
			}
			
			inputStream.close();
			inputStream = null;
			
			buffer[0] = 0;
			out.write(buffer,0,1);
			out.flush();
			
			
			if(checkAck(in)!=0)
				System.out.println("Erro no envio de dados");
			
			out.close();
			
			channel.disconnect();
			session.disconnect();
			
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			
			try {
				if(inputStream != null){
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	//status: 0 - ok, 1 - error, 2 - fatal error
	static int checkAck(InputStream in) throws IOException{
		int status = in.read();
		if(status == 0)
			return status;
		if(status == -1)
			return status;
		if(status == 1 || status == 2){
			StringBuffer buffer = new StringBuffer();
			int c;
			do{
				c = in.read();
				buffer.append((char)c);
			}while(c!='\n');
			if(status==1)
				System.out.println("Exibindo dados:"+buffer.toString());
			if(status==2)
				System.out.println("Exibindo dados:"+buffer.toString());
		}
		return status;
	}
	


}