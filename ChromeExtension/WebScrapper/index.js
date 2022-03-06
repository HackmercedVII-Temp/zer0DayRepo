const axios = require('axios')
const cheerio = require('cheerio')

async function main() {
    let {data} = await axios.get('https://web-scraper-tutorial.netlify.app/')
    let $ = cheerio.load(data)
    let text = $('div.col-12 > h2.favourite').text()
    //let text = $('div.container.bg-secondary > p.text-warning').text()
    console.log(text)
  }
  main()
  