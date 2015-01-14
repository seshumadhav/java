package com.smc.aws.s3.s3logger;
import java.io.File;

public interface S3Handler {
  
  File readFile(String bucketName, String key) throws S3Exception;

  /**
   * Accesses S3 and identifies if S3 account contains a given bucketName.
   * If bucketName does not exist, an exception is thrown.
   * 
   * If bucket exists, write the file with the given key into the bucketName mentioned
   * 
   * @param bucketName
   * @param key
   *
   * @throws S3Exception if bucketName does not exist in the S3 account
   */
  void writeFile(String bucketName, String key, File file);
  
  boolean doesBucketExist(String bucketName);

}
