
***Asteroid Radar***

***The purpose***

My second project for the Udacity Kotlin Android Developer Nanodegree.
The purpose of this project is to test my ability to fetch data from the web ,store it locally
and display it in the UI,regardless if internet connectivity is available.

Libraries used include : ViewModel,RecyclerView,Retrofit,Moshi,Picasso,Room,WorkManager,
Shimmer and Timber.

***App functionality overview***

The Asteroids Radar fetches near - Earth asteroid data from the NASA's NeoWs API.
It also features NASA's image of the day ,which is fed from the NASA's APOD API.
The data is organized in a list with clickable items ,containing asteroid data.
The list can be filtered to show data for today , seven days ahead or previously saved data.
Once clicked,each item leads to a details screen,where detailed asteroid information is being shown.
The image,displayed on the details screen, changes depending on whether the asteroid poses
potential danger or not.
In case the app is installed for the first time and no internet is available the user is presented
with an explanation screen ,where a prompt asks for internet connectivity check and a button
allows for another attempt to fetch the data.
Once the data is successfully fetched it will be displayed from a local database,even if
no internet connection is available.
As per project requirements ,if the image of the day happens to be unavailable, a placeholder
is presented.
The app is localized in Bulgarian language in order to demonstrate my ability to localize apps.
The project features worker functionality ,which refreshes the data once per day in the background.
The worker also deletes data,older than today,in order to keep the database as compact as possible
to minimize local storage usage.

***USAGE***

Clone the repo :

`git clone https://github.com/GerganaT/AsteroidRadar.git`

IMPORTANT!!! - To test the app:

1.Obtain an API - key from here:
  https://api.nasa.gov/  

2.Insert the key in the `utils/Constants.kt` against `API_KEY`



***LICENSES AND ATTRIBUTIONS:***

/* Copyright 2021,  Gergana Kirilova

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

***Shimmer***

License
BSD License

For Shimmer software

Copyright (c) 2015, Facebook, Inc. All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice,
  this list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice,
  this list of conditions and the following disclaimer in the documentation
  and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

***Timber***

Copyright 2013 Jake Wharton

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

***App launcher icon***
Image taken from Freepik.com
<a href='https://www.freepik.com/vectors/technology'>Technology vector created by macrovector
- www.freepik.com</a>

***No image icon***

https://www.freeiconspng.com/images/no-image-icon


***SCREENSHOTS***

 



<img src="https://user-images.githubusercontent.com/51824954/128642930-9630c5d3-b2ae-406e-bd9b-d01e5ee38f6f.png" width="45%"></img><img src="https://user-images.githubusercontent.com/51824954/128642935-039f0e2f-e901-4e57-92a5-f7260420612f.png" width="45%"></img>

<img src="https://user-images.githubusercontent.com/51824954/128642934-dd4d8741-4334-4145-85d5-34fa59bc49c5.png" width="45%"></img><img src="https://user-images.githubusercontent.com/51824954/128642932-66caf7a9-6210-4c5d-8b8a-6cb4346da8b4.png" width="45%"></img>

<img src="https://user-images.githubusercontent.com/51824954/128642928-235f0ced-5921-4a08-8bde-6b0042cbcf84.png" width="45%"></img><img src="https://user-images.githubusercontent.com/51824954/128642929-bbd4ead8-317d-4aeb-93f0-f75e06caf891.png" width="45%"></img>

<img src="https://user-images.githubusercontent.com/51824954/128642926-6997b793-9bdc-4f3c-96cc-4757d55d1498.png" width="45%"></img><img src="https://user-images.githubusercontent.com/51824954/128642927-f6d4d2fc-c1a0-41dd-9cf8-0087d673791e.png" width="45%"></img> 


