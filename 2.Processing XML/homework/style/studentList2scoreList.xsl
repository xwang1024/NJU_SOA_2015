<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:school="http://www.nju.edu.cn/schema"
    xmlns:jw="http://jw.nju.edu.cn/schema">

	<xsl:key name="courseId" match="//jw:courseId" use="." />
	<xsl:key name="scoreType" match="//jw:scoreItem/*[substring(name(), string-length(name()) - string-length('Score') +1)='Score']" use="." />     
    
    <xsl:template match="/">
    <课程成绩列表 xmlns="http://jw.nju.edu.cn/schema">
    <xsl:for-each select="//jw:courseId[generate-id(.)=generate-id(key('courseId',.)[1])]">
    <课程成绩 课程编号="{../jw:courseId}" 成绩性质="平时成绩">
    <xsl:variable name="curCourseId" >
		<xsl:value-of select="../jw:courseId" />
    </xsl:variable>
    <xsl:for-each select="//jw:classScore[../jw:courseId=$curCourseId]">
		<成绩>
		<xsl:element name="学号">
			<xsl:value-of select="../../../school:personInfo/school:id" />
		</xsl:element>
		<xsl:element name="得分">
			<xsl:value-of select="." />
		</xsl:element>
		</成绩>
    </xsl:for-each>
    </课程成绩>
    <课程成绩 课程编号="{../jw:courseId}" 成绩性质="作业成绩">
    <xsl:variable name="curCourseId" >
		<xsl:value-of select="../jw:courseId" />
    </xsl:variable>
    <xsl:for-each select="//jw:homeworkScore[../jw:courseId=$curCourseId]">
		<成绩>
		<xsl:element name="学号">
			<xsl:value-of select="../../../school:personInfo/school:id" />
		</xsl:element>
		<xsl:element name="得分">
			<xsl:value-of select="." />
		</xsl:element>
		</成绩>
    </xsl:for-each>
    </课程成绩>
    <课程成绩 课程编号="{../jw:courseId}" 成绩性质="期中成绩">
    <xsl:variable name="curCourseId" >
		<xsl:value-of select="../jw:courseId" />
    </xsl:variable>
    <xsl:for-each select="//jw:midTermScore[../jw:courseId=$curCourseId]">
		<成绩>
		<xsl:element name="学号">
			<xsl:value-of select="../../../school:personInfo/school:id" />
		</xsl:element>
		<xsl:element name="得分">
			<xsl:value-of select="." />
		</xsl:element>
		</成绩>
    </xsl:for-each>
    </课程成绩>
    <课程成绩 课程编号="{../jw:courseId}" 成绩性质="期末成绩">
    <xsl:variable name="curCourseId" >
		<xsl:value-of select="../jw:courseId" />
    </xsl:variable>
    <xsl:for-each select="//jw:endTermScore[../jw:courseId=$curCourseId]">
		<成绩>
		<xsl:element name="学号">
			<xsl:value-of select="../../../school:personInfo/school:id" />
		</xsl:element>
		<xsl:element name="得分">
			<xsl:value-of select="." />
		</xsl:element>
		</成绩>
    </xsl:for-each>
    </课程成绩>
    <课程成绩 课程编号="{../jw:courseId}" 成绩性质="总评成绩">
    <xsl:variable name="curCourseId" >
		<xsl:value-of select="../jw:courseId" />
    </xsl:variable>
    <xsl:for-each select="//jw:totalScore[../jw:courseId=$curCourseId]">
		<成绩>
		<xsl:element name="学号">
			<xsl:value-of select="../../../school:personInfo/school:id" />
		</xsl:element>
		<xsl:element name="得分">
			<xsl:value-of select="." />
		</xsl:element>
		</成绩>
    </xsl:for-each>
    </课程成绩>
    </xsl:for-each>
    <!--
    <xsl:element name="课程成绩">
		<xsl:attribute name="课程编号">
			<xsl:value-of select="../jw:courseId" />
		</xsl:attribute>
		<xsl:attribute name="成绩性质">
			<xsl:choose>
				<xsl:when test="name()='classScore'">平时成绩</xsl:when>
				<xsl:when test="name()='homeworkScore'">作业成绩</xsl:when>
				<xsl:when test="name()='midTermScore'">期中成绩</xsl:when>
				<xsl:when test="name()='endTermScore'">期末成绩</xsl:when>
				<xsl:when test="name()='totalScore'">总评成绩</xsl:when>
				<xsl:otherwise>其他成绩</xsl:otherwise>
			</xsl:choose>
		</xsl:attribute>
		<成绩>
		<xsl:element name="学号">
			<xsl:value-of select="../../../school:personInfo/school:id" />
		</xsl:element>
		<xsl:element name="得分">
			<xsl:value-of select="." />
		</xsl:element>
		</成绩>
    </xsl:element>-->
    </课程成绩列表>
    </xsl:template>
</xsl:stylesheet>