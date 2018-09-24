
/*
 * No copy rights. Inqude built the below sample using public information available at
 *  https://docs.amazonaws.cn/en_us/sns/latest/dg/SMSMessages.html
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance with
 * the License. A copy of the License is located at
 * 
 * http://aws.amazon.com/apache2.0
 * 
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
 
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SetSMSAttributesRequest;

public class AWSSNSSMSTest {
	public static void main(String args[]) {
        String region="us-east-4";//Please change this as required, I have set it to a dummy
        ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
        try {
            credentialsProvider.getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (/Users/xxxx/.aws/credentials), and is in valid format.",
                    e);
        }
        
        AmazonSNS service = AmazonSNSClientBuilder.standard().withCredentials(credentialsProvider).withRegion(region).build();
        //Set the below parameters accordingly to the ones used in 
        // https://console.aws.amazon.com/sns/v2/home?region=us-east-4#/text-messaging/preferences
        SetSMSAttributesRequest setRequest = new SetSMSAttributesRequest()
    			.addAttributesEntry("DefaultSenderID", "mySenderID")
    			.addAttributesEntry("MonthlySpendLimit", "1")
    			.addAttributesEntry("DeliveryStatusIAMRole", 
    					"arn:aws:iam::123456789012:role/mysmsFeedback")
    			.addAttributesEntry("DeliveryStatusSuccessSamplingRate", "10")
    			.addAttributesEntry("DefaultSMSType", "Transactional")
    			.addAttributesEntry("UsageReportS3Bucket", "sms-xyz-transactional-abc-report");
        service.setSMSAttributes(setRequest);
        
        String message = "My another SMS message";
        String phoneNumber = "+91xxxyyyzzza";
        Map<String, MessageAttributeValue> smsAttributes = 
                new HashMap<String, MessageAttributeValue>();
        
        service.publish(new PublishRequest()
                .withMessage(message)
                .withPhoneNumber(phoneNumber)
                .withMessageAttributes(smsAttributes));
    	}
}
