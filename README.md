# Lure

## Problem

In today's world, carelessly browsing the internet can be dangerous. Due to the rise of phishing and scamming attempts, people are losing out on time and money. Majority of the phishing attempts use big name brands such as Google, Apple, Paypal, and etc.. It's easy for many people to glance at a big name brand's logo and immediately trust the site.

## Product pitch

Our product is a Chrome extension that detects whether the site is a phishing site or not through AI and alerts the user whether or not they want to proceed to the website. Users can adjust how secure they want to be with low, medium, and high settings.

## Technologies we utilized

The primary languages we used were: 
- HTML, CSS, JavaScript, Java, MySQL, Node.js, Express, Jquery, UNIX, Regedicts. 

The frameworks we used were: 
- Google Cloud, Google Vision, Spring Boot, Hibernate, Wordpress, Php, myAdmin, and CloudFlare

## Challenges

There were a lot of challenges that we faced during this hackathon. We ran into issue with implementing the Worpress site. Converting different image types to image types that google vision supports. Researching and developing the webscraper for our backend. Overall, using unfamiliar technologies and languages which led to a lot of researching and confusion. Getting

## What we learned

Learned the basics on using new languages and technologies. Source control to collaborate with each other, and used GitHub project management to manage our tasks. Learned a lot from our tech lead, Jason.

## The future

Improving on detection of phishing sites and creating a sign in/up function for our users.

## API Link:

[POST] https://api.scam0.tech/CalculateConfidence

*Required Form Data[RAW]:
{
   "Domain":"<Website URL>",
   "Image_URLS":["<Website Image Url>","", ...],
   "Site_Text":["<Website HTML Text>","", ...],
   "Colors":["<Website Theme Colors>","", ...]
}

Returns:

{
    "Confidence-Level":<Confidence Level of Website (Higher is more trustworthy, from 0 - 100)>,
    "Most-Likely-Impersonation":<Most Likely Website This Site is Attempting To Impersonate>
}


[GET] https://api.scam0.tech/DecodeImage?image64=<Base 64 encoded link of your logo url here>

Returns: <Company Name of Logo Uploaded>




