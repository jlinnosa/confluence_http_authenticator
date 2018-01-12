package shibauth.confluence.authentication.shibboleth;

import junit.framework.TestCase;
import java.util.*;
import org.junit.*;
import java.util.regex.Pattern;
import static org.junit.Assert.*;

public class RemoteAuthenticatorTest extends TestCase {
    
    public void testConfigReplacement() {
      //ShibAuthConstants.REMOTEUSER_PREFIX
      // #remoteuser.replace=#,A,%,c,(-|TTT),,
      // read from string, may be better read from properties  
      String remoteUserReplace = "\\.,,#,A,%,c,c,n,(-|TTT),,";
      
      List<String> remoteuserlabels  = null;
      //test
      try {
        Pattern pattern = Pattern.compile(remoteUserReplace);
        // fixed bug here to..
        remoteuserlabels = StringUtil.toListOfStringsDelimitedByCommaOrSemicolon(
                pattern.toString());
      } catch (Exception e) {
        fail(e.getMessage());
      }
      
      for (String term : remoteuserlabels) {
        System.out.println("matched:" + term);
      }
      String remoteUser = "#lbert.EinstTTTei%";
      if (remoteuserlabels == null) {
        fail();
      }
     
      remoteUser = remoteUserCharsReplacement(remoteUser, remoteuserlabels); 
      System.out.println("remoteUser:" + remoteUser);
      assertEquals("AlbertEinstein",remoteUser);
     
    }
    
     private String remoteUserCharsReplacement(String remoteUser, List replacements) {
             Iterator it = replacements.iterator();
        while (it.hasNext()) {
              String replaceFromRegex = it.next().toString();
             
              if (!it.hasNext()) {
                  if (replaceFromRegex.length() != 0) {
                    System.out.println("Character replacements specified for Remote User regex is incomplete, make sure the entries are pair-wise, skipping...");
                  }
                  break;
              }
              if (replaceFromRegex.length() == 0) {
                System.out.println("Empty string is found in Remote User replaceFrom regex, skipping...");
                continue;
              }
              String replacement = it.next().toString();
              remoteUser = remoteUser.replaceAll(replaceFromRegex, replacement);
        }
        return remoteUser;
     }
     
    
}
