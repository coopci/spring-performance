# spring-performance

I build this to show the fact that Java is fast and good, Spring XML is not that fast and good, and Spring auto-scan is even much worse than Spring XML. For myself, I don't need this kind of test to know the difference among the three, since I know how computers work. This project is built for the ones who don't see the obvious fact.

gubo.raw.RawMain.java, gubo.springxml.HugeXmlApp.java, gubo.autoscan.AutoScanApp.java do roughly the same thing: new one object for every of the 4900 classes, and establish connections between the objects through their `member` field.


On my Intel Core i5-5300U, 8GB, Windows 7 laptop:


gubo.raw.RawMain.java typically runs slightly longer than 1 second and shorter than 1.5 seconds.


gubo.springxml.HugeXmlApp.java typically runs about 8 seconds.


gubo.autoscan.AutoScanApp.java typically runs between 33 seconds and 35 seconds.


How to run:
1. git clone.
2. Open this project with eclipse.
3. Run gen-class.py from command line.
4. Refresh the project in eclipse. 
5. Run gubo.raw.RawMain.java, gubo.springxml.HugeXmlApp.java and gubo.raw.RawMain.java in eclipse.


suggestion:

So if your autoscan app is insanely slow to start, refactor it using xml, it is highly probable it becomes tolerably slow.

I admit, it is eaier to use Spring than not to even build this test. But isn't it the very responsility of us developer to overcome difficulties at develop time to deliver softwares run better and faster at runtime. 


Again, one thing Jesus and Spring have in common is that their death saves us all.