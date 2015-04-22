NJU_SOA_2015
========

01. Schema设计
-------------

    设计关于学生信息的相关schema，要求:
    1. 按要求分离公共数据
    2. 数据结构合理且满足实际应用需求
    3. 使用任意工具（WTP XML Schema Editor）
    4. 以小组中学号最小的同学作为例，编写一个符合Student.xsd数据类型的XML文档1
    

02. Processing XML
-------------

    1. 设计StudentList.xsd
    2. 采用DOM生成符合StudentList.xsd要求的XML文档2除写入Assignment1中的XML文档1的同学信息外，
       还需要加入小组中其他同学的相关信息，并为每位同学随机生成五门课程的成绩（包含平时成绩、期末成绩和总评成绩），
       要求至少有1名同学，任意成绩低于60分。
    3. 采用XSLT将该XML文档转换为符合ScoreList.xsd格式要求的成绩册文档XML文档3。
    4. 采用SAX处理XML文档3 ，仅保留出现任意不及格成绩的课程成绩记录，保存为XML文档4
    5. 提交相关脚本、程序和readme.txt
    注意：如相关schema与ScoreList.xsd不兼容，则需要对相关schema作出修改。
