import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;

import com.smc.aws.s3.s3logger.S3ConnectionFactory;
import com.smc.aws.s3.s3logger.S3Handler;
import com.smc.aws.s3.s3logger.S3HandlerImpl;

public class MyS3Sample {

  public static void main(String[] args) throws IOException {
	File file = createSampleFile();

	S3Handler s3Handler = new S3HandlerImpl(S3ConnectionFactory.getS3());
	s3Handler.writeFile("test-for-s3-api", file.getName(), file);
  }

  private static File createSampleFile() throws IOException {
	File file = File.createTempFile("aws-java-sdk---", ".txt");
	file.deleteOnExit();

	Writer writer = new OutputStreamWriter(new FileOutputStream(file));
	writer.write("abcdefghijklmnopqrstuvwxyz\n");
	writer.write("01234567890112345678901234\n");
	writer.write("!@#$%^&*()-=[]{};':',.<>/?\n");
	writer.write("01234567890112345678901234\n");
	writer.write("abcdefghijklmnopqrstuvwxyz\n");
	writer.write("Timestamp: " + new Date());
	writer.close();

	return file;
  }

}
