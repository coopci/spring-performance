# spring-performance

I build this to show the fact that Java is fast and good, Spring XML is not that fast and good, and Spring @Autowired is even much worse than Spring XML. 

gubo.raw.RawMain.java, gubo.springxml.HugeXmlApp.java, gubo.autoscan.AutoScanApp.java, gubo.annoconfig.AnnoConfigApp.java do roughly the same thing: new one object for every of the 4900 classes, and establish connections between the objects through their `member` field.


On my Intel Core i5-5300U, 8GB, Windows 7 laptop:


gubo.raw.RawMain.java typically runs slightly longer than 1 second and shorter than 1.5 seconds.


gubo.springxml.HugeXmlApp.java typically runs about 8 seconds.


gubo.autoscan.AutoScanApp.java(@Autowired with or without @Qualifier) typically runs between 33 seconds and 35 seconds.

gubo.annoconfig.AnnoConfigApp.java typically runs between 33 seconds and 35 seconds.


On my MacBook Pro (15-inch, 2017) 3.1 GHz Intel Core i7, 16 GB 2133 MHz LPDDR3

gubo.autoscan.AutoScanApp.java(@Resource with or without @Qualifier) typically runs between 5.517 seconds and 6.345 seconds.

gubo.springxml.HugeXmlApp.java typically runs between 3.444 seconds and 4.635 seconds.


The only thing I didn't anticipated is that AutoScanApp.java and AnnoConfigApp.java take about same amount of time, I thought AutoScanApp.java would run significantly longer than AnnoConfigApp.java.


How to run:
1. git clone.
2. Open this project with eclipse.
3. Run gen-class.py from command line.
4. Refresh the project in eclipse. 
5. Run gubo.raw.RawMain.java, gubo.springxml.HugeXmlApp.java and gubo.raw.RawMain.java in eclipse.


suggestion:

So if your annotation-configed app is insanely slow to start, refactor it using xml, it is highly probable that it becomes tolerably slow. Or if you are realy lazy, just replacing @Autowired with @Resource would also reduce your wasted time, but it is wasted any way since you use spring...

I admit, it is slightly(just slightly, no more) eaier to use Spring than not to even building this test. But isn't it the very responsility of us developer to overcome difficulties at develop time to deliver softwares run better and faster at runtime. 

