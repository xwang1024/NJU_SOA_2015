<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <课程成绩列表>
            <xsl:apply-templates />
        </课程成绩列表>
    </xsl:template>
    <xsl:key name="groupByCourseId" match="classScore|homeworkScore|midTermScore|endTermScore|totalScore" use="concat(../courseId,'+',.)"></xsl:key>

    <xsl:template match="studentList">
        <xsl:apply-templates
            select="//classScore|homeworkScore|midTermScore|endTermScore|totalScore[generate-id(.)=generate-id(key('groupByCourseId',concat(../courseId,'+',.))[1])]"
            mode="inGroup" />
    </xsl:template>

    <xsl:template match="//classScore|homeworkScore|midTermScore|endTermScore|totalScore" mode="inGroup">
        <课程成绩>
            <xsl:attribute name="课程编号"><xsl:value-of select="../courseId" /></xsl:attribute>
            <xsl:attribute name="成绩性质">
            	<xsl:choose>
            		<xsl:when test="name()='classScore'">
            			平时成绩
            		</xsl:when>
            		<xsl:when test="name()='homeworkScore'">
            			作业成绩
            		</xsl:when>
            		<xsl:when test="name()='midTermScore'">
            			期中成绩
            		</xsl:when>
            		<xsl:when test="name()='endTermScore'">
            			期末成绩
            		</xsl:when>
            		<xsl:when test="name()='totalScore'">
            			总评成绩
            		</xsl:when>
            		<xsl:otherwise>
            		</xsl:otherwise>
            	</xsl:choose>
            </xsl:attribute>
            
            <xsl:for-each select="key('groupByCourseId', concat(../courseId,'+',.))">
                <成绩>
                    <学号>
                        <xsl:value-of select="../../../personInfo/id" />
                    </学号>
                    <得分>
                        <xsl:value-of select="." />
                    </得分>
                </成绩>

            </xsl:for-each>
        </课程成绩>
    </xsl:template>

</xsl:stylesheet>