package com.smc.aws.s3.s3logger;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

public class S3ConnectionFactory {
  
  public static final AmazonS3 getS3() {
    AmazonS3 s3 = new AmazonS3Client(new ClasspathPropertiesFileCredentialsProvider());
	Region usWest2 = Region.getRegion(Regions.US_WEST_2);
	s3.setRegion(usWest2);
	return s3;
  }

}
