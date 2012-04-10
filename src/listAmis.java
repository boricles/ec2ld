DescribeImagesResult imgList = ec2.describeImages();
Iterator<Image> imgs = imgList.getImages().iterator();

int i=0;
while(imgs.hasNext())
{
	Image img = imgs.next();
	String plat = img.getPlatform();
	System.out.println("img(" + i++ + "):" + img + " " + plat);
}
		