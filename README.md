# Capture
Mindvalley Test -  Image Loading library

- Language     - Java
- Architecture - MVVM
- Libraries    - Dagger
- Use the following url for loading data: http://pastebin.com/raw/wgkJgazE

#### To get JSON response
------

```java
Capture.with().load(url).listener(new CaptureJsonCallback() {
            @Override
            public void getJson(String string) {
                //TODO : implement methods to parse JSON response
            }

            @Override
            public void onFailedDownload() {
                //TODO :
            }
        }).build();
 ```
 
 #### To directly load Image to imageview
 ------
 
 ```java
 Capture.with().load(imageUrl).into(view).show();
 ```
 
 #### To get Image to listener
 ```java
        Capture.with().load(imageUrl).listener(new CaptureImageCallback() {
            @Override
            public void getImage(Bitmap bitmap) {

            }

            @Override
            public void onFailedDownload() {

            }
        }).build();
```
 
