![CF-Predictor-logo](https://github.com/WslF/CF-rating-prediction/blob/master/Files/CF-Predictor-icons/icon1024.png?raw=true)

A huge number of your nerve cells die every time when you wait for a rating update on Codeforces. Stop this! From now you could use this service, it calculates approximate rating changes for every contestant.

FOR USERS
----
The most interested thing for you is chrome extension. It partly modifies the contest standings page and shows approximate rating changes for every contestant. You could find it [here][1].
![alt tag](https://github.com/WslF/CF-rating-prediction/blob/master/Files/ExtensionScreenShot1.png?raw=true)

Also you could find more detailed information (seed, rank, expected delta, etc.) [here][2]
![alt tag](https://github.com/WslF/CF-rating-prediction/blob/master/Files/SiteScreenShot1.png?raw=true)

A project currently in beta, so predictions are not very accurate. Average mistake around 5 points, but for the contestants at the back of standings it could be greater up to a few hundreds.

FOR DEVELOPERS
---

Project consist of 3 main parts.


| № | assignment | path | language |
|:---- | :------ | :------ | :-------|
| 1 | compute role | CF-PredictorBackEnd | Java8, Tomcat |
| 2 | web role | CF-PredictorFrontEnd | Java8, Tomcat |
| 3 | chrome extension | CF-Predictor-Extension | JavaScript (with using jQuerry) |


**Compute role** send requests to codeforces, compute approximate rating changes and store some of them in the cache. Every cached value recomputes once a few minutes.

**Web role** just get rating prediction from compute role and show it in human readable view.

Currently compute & web roles runs on aws Elastic Beanstalk. Servers run only in the contest's days to prevent overusing free tier resources.

**Chrome extension** send request to web role, get JSON response and partly modify the contest standings page.

If you want to contribute to the project or have any questions, you could reach me by codeforces [profile][3] or email WslF@i.ua


DONATE
---

BTC wallet: `1Gn2TaSqRUwGFmfeseDphHV9dGR8YAzvim`

  [1]: https://chrome.google.com/webstore/detail/rating-predictor-for-code/ocfloejijfhhkkdmheodbaanephbnfhn
  [2]: http://cf-predictor.us-west-2.elasticbeanstalk.com/
  [3]: http://codeforces.com/profile/Wsl_F
 

