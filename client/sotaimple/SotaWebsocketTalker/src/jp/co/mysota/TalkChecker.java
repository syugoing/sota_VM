//このソースは、VstoneMagicによって自動生成されました。
//ソースの内容を書き換えた場合、VstoneMagicで開けなくなる場合があります。
package	jp.co.mysota;
import	main.main.GlobalVariable;
import	jp.vstone.RobotLib.*;
import	jp.vstone.sotatalk.*;
import	jp.vstone.sotatalk.SpeechRecog.*;
import	java.io.*;
import	jp.vstone.camera.*;
import	jp.vstone.camera.*;
import	java.awt.image.BufferedImage;
import	java.io.ByteArrayOutputStream;
import	java.io.File;
import	java.io.FileOutputStream;
import	java.io.IOException;
import	javax.imageio.ImageIO;
import	java.util.Base64;

public class TalkChecker
{

	public String speechRecogResult;
	public RecogResult recogresult;
	public jp.co.mysota.WebsocketMessenger WebsocketMessenger;
	public TalkChecker()																								//@<BlockInfo>jp.vstone.block.func.constructor,16,16,496,16,False,4,@</BlockInfo>
	{
																														//@<OutputChild>
		/*String speechRecogResult*/;																					//@<BlockInfo>jp.vstone.block.variable,80,16,80,16,False,3,break@</BlockInfo>
																														//@<EndOfBlock/>
		/*RecogResult recogresult*/;																					//@<BlockInfo>jp.vstone.block.variable,144,16,144,16,False,2,break@</BlockInfo>
																														//@<EndOfBlock/>
		WebsocketMessenger=new jp.co.mysota.WebsocketMessenger();														//@<BlockInfo>jp.vstone.block.variable,208,16,208,16,False,1,break@</BlockInfo>
																														//@<EndOfBlock/>
																														//@</OutputChild>
	}																													//@<EndOfBlock/>

	//@<Separate/>
	public void getPicture(String message)																				//@<BlockInfo>jp.vstone.block.func,112,352,384,496,False,7,@</BlockInfo>
	throws SpeechRecogAbortException {
		if(!GlobalVariable.TRUE) throw new SpeechRecogAbortException("default");

																														//@<OutputChild>
		{																												//@<BlockInfo>jp.vstone.block.facedetect.stillpicture,176,352,176,352,False,6,still@</BlockInfo>
			String filepath = "/var/sota/photo/";
			filepath += (String)"picture";
			boolean isTrakcing=GlobalVariable.robocam.isAliveFaceDetectTask();
			if(isTrakcing) GlobalVariable.robocam.StopFaceTraking();
			GlobalVariable.robocam.initStill(new CameraCapture(CameraCapture.CAP_IMAGE_SIZE_5Mpixel, CameraCapture.CAP_FORMAT_MJPG));
			GlobalVariable.robocam.StillPicture(filepath);

			CRobotUtil.Log("stillpicture","save picthre file to \"" + filepath +"\"");
			if(isTrakcing) GlobalVariable.robocam.StartFaceTraking();
		}																												//@<EndOfBlock/>
		ByteArrayOutputStream baos = null;																				//@<BlockInfo>jp.vstone.block.freeproc,320,496,320,496,False,5,@</BlockInfo>
			FileOutputStream fileOuputStream = null;

			try {
			    BufferedImage originalImage = ImageIO.read(new File(
				    "/var/sota/photo/picture.jpg"));

			    baos = new ByteArrayOutputStream();
			    ImageIO.write(originalImage, "jpg", baos);
			    baos.flush();
			    byte[] imageInByte = baos.toByteArray();
			    

			    
			   fileOuputStream = new FileOutputStream(
				    "/var/sota/photo/convert_picture.jpg");
			    fileOuputStream.write(imageInByte);	    

			    
		                  
		                 String result = new String(imageInByte, "UTF-8");
		                 String encoded = Base64.getMimeEncoder().encodeToString(result.getBytes());


		                 System.out.println("Conversion completed");
		 
		                  speechRecogResult = encoded;


			} catch (IOException e) {
			    e.printStackTrace();
			}finally{
			    try {
				baos.close();
				fileOuputStream.close();
			    } catch (IOException e) {
				e.printStackTrace();
			    } 
		}
																														//@<EndOfBlock/>
																														//@</OutputChild>

	}																													//@<EndOfBlock/>

	//@<Separate/>
	/*
	speechRecogResult = GlobalVariable.recog.getResponsewithAbort((int)10000,(int)3);									//@<BlockInfo>jp.vstone.block.talk.speechrecog.get,16,352,16,352,False,8,音声認識して、得られた結果（文字列）をspeechRecogResultに代入します。@</BlockInfo>
	if(speechRecogResult == null) speechRecogResult = "";

																														//@<EndOfBlock/>

	*/

	//@<Separate/>
	public void talkCheck()																								//@<BlockInfo>jp.vstone.block.func,16,192,1600,192,False,10,@</BlockInfo>
	throws SpeechRecogAbortException {
		if(!GlobalVariable.TRUE) throw new SpeechRecogAbortException("default");

																														//@<OutputChild>
		WebsocketMessenger.connect((String)"ws://192.168.128.100:8080/ws");												//@<BlockInfo>jp.vstone.block.callfunc.base,144,192,144,192,False,9,@</BlockInfo>	@<EndOfBlock/>
		while(GlobalVariable.TRUE)																						//@<BlockInfo>jp.vstone.block.while.endless,512,144,1072,144,False,15,Endless@</BlockInfo>
		{

																														//@<OutputChild>
			recogresult = GlobalVariable.recog.getRecognitionwithAbort((int)60000);										//@<BlockInfo>jp.vstone.block.talk.speechrecog.score2,592,96,848,96,False,14,音声認識を行い、認識候補との完全一致で比較する。認識スコアが一番高い結果に分岐する。実際に認識された文字列はspeechRecogResultに代入される@</BlockInfo>
			speechRecogResult = recogresult.CheckBest(new String[]{
			 "頑張って" ,  "" , 
			},false);
			if(speechRecogResult == null) speechRecogResult = "";

			if(speechRecogResult.contains((String)"頑張って"))
			{
				speechRecogResult = recogresult.getBasicResult();
				if(speechRecogResult == null) speechRecogResult = "";

																														//@<OutputChild>
					GlobalVariable.sotawish.Say((String)"I am perfect robot. ありがとう！これからもよろしくね",MotionAsSotaWish.MOTION_TYPE_TALK,(int)11,(int)13,(int)11);	//@<BlockInfo>jp.vstone.block.talk.say,656,96,656,96,False,12,@</BlockInfo>
																															//@<EndOfBlock/>
					getPicture((String)speechRecogResult);																	//@<BlockInfo>jp.vstone.block.callfunc.base,736,96,736,96,False,11,@</BlockInfo>	@<EndOfBlock/>
																																//@</OutputChild>

			}
			else
			{
				speechRecogResult = recogresult.getBasicResult();
				if(speechRecogResult == null) speechRecogResult = "";

																														//@<OutputChild>
																														//@</OutputChild>

			}
																														//@<EndOfBlock/>
			WebsocketMessenger.sendMessage((String)speechRecogResult);													//@<BlockInfo>jp.vstone.block.callfunc.base,992,144,992,144,False,13,@</BlockInfo>	@<EndOfBlock/>
																														//@</OutputChild>
		}
																														//@<EndOfBlock/>
																														//@</OutputChild>

	}																													//@<EndOfBlock/>

}
