/* Inqude allows this example to be copied and reused for any purpose
* The below example has been developed with exmaple content provided by Amazon documentation
*/

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AdminGetUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminGetUserResult;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.ListUsersRequest;
import com.amazonaws.services.cognitoidp.model.ListUsersResult;
import com.amazonaws.services.cognitoidp.model.UserType;

public class AWSGetUserDetails {
	public static void main(String args[]) {
		
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
        
    	AWSCognitoIdentityProvider identityClient = AWSCognitoIdentityProviderClientBuilder.standard().withCredentials(credentialsProvider).withRegion("us-east-4").build();

		List<String> adminEmailIds = new ArrayList<String>();
		ListUsersRequest listUserRequest = new ListUsersRequest().withUserPoolId("us-east-4_abcdef123");
		String filter = "sub = \"9999f46a-a2a2-c2c2-b2b2-1b203469b0aa\"";
		listUserRequest.setFilter(filter);
		ListUsersResult result = identityClient.listUsers(listUserRequest);
		if (result != null) {
			List<UserType> users = result.getUsers();
			for (UserType user : users) {
				List<AttributeType> attrs = user.getAttributes();
				String phone = getPhone(attrs);
				System.out.println("phon: "+phone);
			}
		}
	}
		

private static String getPhone(List<AttributeType> attrs) {

/*
* username (case-sensitive)
* email
* phone_number
* name
* given_name
* family_name
* preferred_username
* cognito:user_status (called Status in the Console) (case-insensitive)
* status (called Enabled in the Console) (case-sensitive)
* sub
*/

	for (AttributeType type : attrs) {
		if (type.getName().equals("phone_number")) {
			return type.getValue();
		}

	}
	return null;
}
}
