package com.wslfinc.cf.sdk.api;

import com.wslfinc.cf.sdk.entities.User;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author wslf
 */
public class UserAPITest {

    public UserAPITest() {
    }

    @Test
    public void testSomeMethod() {
        //Test correct on 31 Jan 2017
        User user = UserAPI.getUserInfo("Wsl_F");
        assertNotNull(user);

        long lastOnlineTime_expected = 1485289253;
        long lastOnlineTime_received = user.getLastOnlineTimeSeconds();
        //assertEquals(lastOnlineTime_expected, lastOnlineTime_received);

        int rating_expected = 1910;
        int rating_received = user.getRating();
//        assertEquals(rating_expected, rating_received);

        String avatar_expected = "http://userpic.codeforces.com/68351/avatar/18d74400140d8218.jpg";
        String avatar_received = user.getAvatar();
        assertEquals(avatar_expected, avatar_received);

        int contribution_expected = 77;
        int contribution_received = user.getContribution();
        //assertEquals(contribution_expected, contribution_received);

        String organization_expected = "Kyiv NU";
        String organization_received = user.getOrganization();
        assertEquals(organization_expected, organization_received);

        String rank_expected = "candidate master";
        String rank_received = user.getRank();
        assertEquals(rank_expected, rank_received);

        int maxRating_expected = 2018;
        int maxRating_received = user.getMaxRating();
        assertEquals(maxRating_expected, maxRating_received);

        long regTime_expected = 1349978778;
        long regTime_received = user.getRegistrationTimeSeconds();
        assertEquals(regTime_expected, regTime_received);

        String maxRank_expected = "candidate master";
        String maxRank_received = user.getMaxRank();
        assertEquals(maxRank_expected, maxRank_received);

        String handle_expected = "Wsl_F";
        String handle_received = user.getHandle();
        assertEquals(handle_expected, handle_received);

        String lastName_expected = "Franchuk";
        String lastName_received = user.getLastName();
        assertEquals(lastName_expected, lastName_received);

        String firstName_expected = "Vasyl";
        String firstName_received = user.getFirstName();
        assertEquals(firstName_expected, firstName_received);

        String country_expected = "Ukraine";
        String country_received = user.getCountry();
        assertEquals(country_expected, country_received);

//        String city_expected = "Kyiv";
//        String city_received = user.getCity();
//        assertEquals(city_expected, city_received);

        int friendOfCount_expected = 42;
        int friendOfCount_received = user.getFriendOfCount();
        //assertEquals(friendOfCount_expected, friendOfCount_received);

        String titlePhoto_expected = "http://userpic.codeforces.com/68351/title/c2c321f53e7eb744.jpg";
        String titlePhoto_received = user.getTitlePhoto();
        assertEquals(titlePhoto_expected, titlePhoto_received);
    }

}
