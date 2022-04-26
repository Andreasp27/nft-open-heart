NFT-OpenHeart
OpenHeart is a marketplace

Our Team on this Project :
- [Andreas Gibran Nethanel Paat](https://github.com/Andreasp27) (672019092)
- [Rafael Jonathan](https://github.com/RafaelJo17) (672019090)

# Web Service & App Project in the same Repositories
>https://github.com/Andreasp27/nft-open-heart.git

# Specification :
1. Theme NFT Marketplace ***[Our Own BrainStorming]***
2. UI ***[https://www.figma.com/file/OHFxDCmEhOlc6HelKv0ioK/Untitled]***
3. Adapter and Recyclerview ***[Almost Using In Every Single Activity]*** 
4. Intent ***[Intent used every single activity changes]***
5. Activity ***[Activity used frequently]*** 
6. Fragment ***[Used for Navigation bar and more]*** 
7. onActivityResult ***[Image Posting]*** 
8. Media ***[Profile Picture, Banner and etc]*** 
9. Permission : Used for permission Internet, Media, Fine Location ***[GPS, Internet, Media]*** 
10. Googlemaps API ***[Used for getting User current location in edit profile]***,
11. Webservice ***[Laravel 8]*** 
12. Database with SQL/MySQL ***[included]***

# Project Installation
1. Open git-bash in your local folder
2. Then choose your folder for project installation
3. And use `git clone` in your git-bash following with our github link https://github.com/Andreasp27/nft-open-heart.git
4. And voila! you have our projects app

# Troubleshooting
### Using Android Virtual Device
1. Make sure you have XAMPP or WAMP installed in your machine
2. Download our latest .SQL file in our repositories
3. Import our sql file into your database management system (i.e HeidiSQL,etc)
### Using Phone
1. Make sure webservice is running and Database is running, also SQL are updated
2. Webservice must running in local ip. Change the serve method to your local IP in your IDE Terminal (i.e php artisan serve --host=***your_local_IP*** --port=80)
3. Edit your base url android studio project in ApiClient.java & HomeActivity.java
### Using our Hosting Service
1. Edit the base url in HomeActivity.java change to `https://openheart.kecapy.com/public/`
2. Edit the base url in ApiClient.java change to `https://openheart.kecapy.com/public/api`
