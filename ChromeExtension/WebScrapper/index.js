const express = require("express");
const axios = require("axios");
const cheerio = require('cheerio');
const bodyParser = require('body-parser');
const fetch = require('node- fetch')
const app = express();//binds the express module to 'app'
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

getAllImg = () => {
    return $('img').map(function () { return $(this).attr('src'); });
}
app.post("http://localhost:8080/CalculateConfidence", function (req, res) {
    var link = req.body.url;
    var temp;
    axios.get(link).then((response) => {
        var imgs = getAllImg(res);
        temp = { "domain": link.split("/", 3), "code": res.data, "image": imgs }

    }, (error) => {
        fetch('http://localhost:8080/CalculateConfidence' , { method: 'POST', body: '{\r\n   \"Domain\":\"https:\/\/sites.asdfes.com\/view\/obvious-scamfor-test-purposes\/home\",\r\n   \"Image_URLS\":[\"https:\/\/d187qskirji7ti.cloudfront.net\/companies\/wide_images\/000\/000\/056\/1518102340_large.png\"\r\n   ],\r\n   \"Site_Text\":[\r\n      \"User ID Must be at least 6 characters long \\r\\n Save this User ID Online ID Help  layer\\r\\nHow does \\\"Save this User ID\\\" work?\\r\\n\\r\\n \\r\\n\\r\\nSaving your User ID means you don\'t have to enter it every time you log in.\\r\\n\\r\\n \\r\\n\\r\\nDon\'t save on a public computer\\r\\n\\r\\n \\r\\n\\r\\nOnly save your User ID on your personal computer or mobile device.\\r\\n\\r\\n \\r\\n\\r\\nHow to clear a saved User ID\\r\\n\\r\\n \\r\\n\\r\\nTo clear a saved User ID, log in and select Saved User IDs from Profile and Settings.\\r\\n\\r\\nPassword is unavailable. Please enter atleast 6 characters of online id to enable Passcode\\r\\nForgot your Password?\\r\\nLog In Log In with mobile app Log In with Password Log in with Windows Hello\\r\\nCheck your mobile device\\r\\nLoading\\r\\nWe sent a notification to your registered device. Verify your identity in the app now to log in to Online Banking.\\r\\n\\r\\nSend notification again Log In with Password instead\\r\\nCheck your mobile device\\r\\nIf you\'re enrolled in this security feature, we sent a notification to your registered device. Verify your identity in the app now to log in to Online Banking.\\r\\n\\r\\nSend notification again Log In with Password instead\\r\\nCheck your mobile device\\r\\nWe can\'t identify you at this time. Please use your User ID\\\/Password to log in.\\r\\n\\r\\nLog In with Password instead\\r\\nStay connected with our app\\r\\n\\r\\nMobile banking Llama\\r\\nSecure, convenient banking anytime\\r\\nGet the app  link opens a new info modal layer\", \"CONGRATULATIONS !!! YOU WON $1,000,000 !!!! REDEEM WHEN YOU LOG IN !!! BANK OF AMERICA USERS ONLY !!!\", \"Give Me All Your Money \", \"Obvious Scam\"\r\n   ],\r\n   \"Colors\":[\r\n      \"FFFFF\",\r\n      \"239423\"\r\n   ]\r\n}'})
.then(results => results.json())
.then(console.log)
        res.sendStatus(400);
    })
    res.send(temp);

});

/*fetch('http://localhost:8080/CalculateConfidence' , { method: 'POST', body: '{\r\n   \"Domain\":\"https:\/\/sites.asdfes.com\/view\/obvious-scamfor-test-purposes\/home\",\r\n   \"Image_URLS\":[\"https:\/\/d187qskirji7ti.cloudfront.net\/companies\/wide_images\/000\/000\/056\/1518102340_large.png\"\r\n   ],\r\n   \"Site_Text\":[\r\n      \"User ID Must be at least 6 characters long \\r\\n Save this User ID Online ID Help  layer\\r\\nHow does \\\"Save this User ID\\\" work?\\r\\n\\r\\n \\r\\n\\r\\nSaving your User ID means you don\'t have to enter it every time you log in.\\r\\n\\r\\n \\r\\n\\r\\nDon\'t save on a public computer\\r\\n\\r\\n \\r\\n\\r\\nOnly save your User ID on your personal computer or mobile device.\\r\\n\\r\\n \\r\\n\\r\\nHow to clear a saved User ID\\r\\n\\r\\n \\r\\n\\r\\nTo clear a saved User ID, log in and select Saved User IDs from Profile and Settings.\\r\\n\\r\\nPassword is unavailable. Please enter atleast 6 characters of online id to enable Passcode\\r\\nForgot your Password?\\r\\nLog In Log In with mobile app Log In with Password Log in with Windows Hello\\r\\nCheck your mobile device\\r\\nLoading\\r\\nWe sent a notification to your registered device. Verify your identity in the app now to log in to Online Banking.\\r\\n\\r\\nSend notification again Log In with Password instead\\r\\nCheck your mobile device\\r\\nIf you\'re enrolled in this security feature, we sent a notification to your registered device. Verify your identity in the app now to log in to Online Banking.\\r\\n\\r\\nSend notification again Log In with Password instead\\r\\nCheck your mobile device\\r\\nWe can\'t identify you at this time. Please use your User ID\\\/Password to log in.\\r\\n\\r\\nLog In with Password instead\\r\\nStay connected with our app\\r\\n\\r\\nMobile banking Llama\\r\\nSecure, convenient banking anytime\\r\\nGet the app  link opens a new info modal layer\", \"CONGRATULATIONS !!! YOU WON $1,000,000 !!!! REDEEM WHEN YOU LOG IN !!! BANK OF AMERICA USERS ONLY !!!\", \"Give Me All Your Money \", \"Obvious Scam\"\r\n   ],\r\n   \"Colors\":[\r\n      \"FFFFF\",\r\n      \"239423\"\r\n   ]\r\n}'})
.then(results => results.json())
.then(console.log)*/

app.listen(3001, function () {
    console.log("SERVER STARTED ON localhost:3001");
})