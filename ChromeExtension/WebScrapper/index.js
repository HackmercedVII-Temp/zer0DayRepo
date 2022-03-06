const PORT = 8000

const axios = require('axios')
const cheerio = require('cheerio')
const express = require('express')

const app = express()

const url = 'https://www.theguardian.com/uk'

//app.METHOD(PATH, HANDLER)

app.get('/', function (req, res){
    res.json('This is my webscraper')
})
//displayed on localhost:8000
app.get('/results', (req, res) => {
    axios(url)
    .then(response => {
        const html = response.data
        //console.log(html)
        const $ = cheerio.load(html)
        const domains = []
        //const titles
        //const color
        $('.fc-item__title', html).each(function () { //<-- cannot be a function expression
            const title = $(this).text()
            const url = $(this).find('a').attr('href')
            domains.push({
                title,
                url
            })
        })
        res.json(domains)
        //console.log(articles)    
    }).catch(err => console.log(err))
})





app.listen(PORT, () => console.log('server running on PORT ${PORT}'))










/*async function main() {
    let {data} = await axios.get('https://web-scraper-tutorial.netlify.app/')
    let $ = cheerio.load(data)
    let text = $('div.col-12 > h2#favourite').text()
    //let text = $('div.container.bg-secondary > p.text-warning').text()
    console.log(text)
*/
/*
loop through elements <p>

let paragraphs = document.querySelectorAll('p')
for(paragraph of paragraphs) {
    let text = paragraph.innerText
    console.log(text)
}
    
jquery sample for console 

let paragraphs = $('p')
for(paragraph of paragraphs) {
   let text = $(paragraph).text()
   console.log(text)
}

  }

  main()






  app.get() //get data 
  
  app.post() //add 

  app.put() //edit
  
  app.delete() //delete data

*/