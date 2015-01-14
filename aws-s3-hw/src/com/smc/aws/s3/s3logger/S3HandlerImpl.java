package com.smc.aws.s3.s3logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

public class S3HandlerImpl implements S3Handler {

  private AmazonS3            s3;
  private static final Logger LOGGER = Logger.getLogger("S3LOG");

  public S3HandlerImpl(AmazonS3 s3) {
	this.s3 = s3;
  }

  @Override
  public void writeFile(String bucketName, String key, File file) {
	LOGGER.log(Level.INFO, "Attempting to write file '" + file.getName()
	    + "' into bucket '" + bucketName + "' with key: '" + key + "'");
	s3.putObject(new PutObjectRequest(bucketName, key, file));
	LOGGER.log(Level.INFO, "Completed putting the file onto S3 > " + bucketName
	    + " > " + key);
  }

  @Override
  public File readFile(String bucketName, String key) throws S3Exception {
	S3Object object = s3.getObject(new GetObjectRequest(bucketName, key));
	S3ObjectInputStream objectContent = object.getObjectContent();
	// File fileRead = readFileFromInputStream("/tmp/from-s3/", object.getKey(),
	// objectContent);
	// return fileRead;

	throw new UnsupportedOperationException(
	    "S3 readFile operation not implemented");
  }

  private File readFileFromInputStream(String filePath, String fileName,
	  S3ObjectInputStream inputStream) throws S3Exception {
	File file = new File(filePath + fileName);
	BufferedReader reader = new BufferedReader(new InputStreamReader(
	    inputStream));

	try {
	  BufferedWriter writer = new BufferedWriter(new FileWriter(file));
	  String line;
	  while ((line = reader.readLine()) != null) {
		writer.write(line);
	  }

	  writer.close();
	  inputStream.close();
	} catch (IOException e) {
	  throw new S3Exception("Unable to read file from S3", e);
	}

	return file;
  }

  @Override
  public boolean doesBucketExist(String bucketName) {
    for (Bucket bucket : s3.listBuckets()) {
      if (bucket.getName().equalsIgnoreCase(bucketName)) {
    	return true;
      }
    }
    
	return false;
  }

}
