package com.kanq.test;

import java.util.ArrayList;
import java.util.List;
/**
 * 问题是这样的：古典问题：有一对兔子，从出生后第3个月起每个月都生一对兔子，小兔子长到第三个月后每个月又生一 对兔子， 假如兔子都不死，问每个月的兔子总数为多少？

   这个问题相信大家已经不在陌生了。很多博客里都有各种不同的解答方法。

   最多的方法就是先列出最初几个月的兔子对数（注意是对数，不是个数）。如下所示：

1,1,2,3,5,8,13,21,34....
然后观察数据的规律，从而得出这样一个结论：从第三个月开始，兔子对数等于前面两个月的兔子对数之和。看到这里相信你已经有写出代码的思路了。这里也不再详细说明了。

   我要介绍的是，不知道这个规律的前提进行编程。

   思路是这样的：

   ①、有一个笼子，我们逐一取出笼子中的一对兔子（当然这两只兔子的年龄是相同的）。

   ②、若它们的年龄大于或等于三个月，则生出一对小兔子。

   ③、将这两对兔子放入笼中。

   ④、所有兔子的年龄加1（上面出生的兔子年龄不加）。

   ⑤、5查看此时笼子中的兔子数量。

   可能文字上理解起来会比较困难。相信你一看代码就明白了。下面是整个代码：
 * @author x1342
 *
 */
public class Rabbit {
	public static void main(String[] args) {
		
		List<littleRabbit> list = new ArrayList<littleRabbit>();
		list.add(new littleRabbit());
		for(int k=1;k<=20;k++){
			for(int j=0;j<list.size();j++){
				littleRabbit rabbit= list.get(j);
				int age= rabbit.getAge();
				if(age>=3){
					list.add(new littleRabbit());
				}
				age++;
				rabbit.setAge(age);
			}
			  System.out.println("第" + k + "个月有" + list.size() + "对兔子，一共"+list.size()*2+"只。");
	        }
	    }
}
	

class littleRabbit{
	private int age=1;
	public void growUp(){
		this.age++;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
}
