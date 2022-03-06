const express = require("express");
const axios = require("axios");
const cheerio = require('cheerio');
const bodyParser = require('body-parser');

const app = express();//binds the express module to 'app'
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

getAllImg = () => {
    return $('img').map(function () { return $(this).attr('src'); });
}
app.post("/url", function (req, res) {
    var link = req.body.url;
    var temp;
    axios.get(link).then((response) => {
        var imgs = getAllImg(res);
        temp = { "domain": link.split("/", 3), "code": res.data, "image": imgs }
    }, (error) => {
        res.sendStatus(400);
    })
    res.send(temp);
});

app.listen(3001, function () {
    console.log("SERVER STARTED ON localhost:3001");
})