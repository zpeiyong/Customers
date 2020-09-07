<#macro DetailPart detail_list detail_type>
        <w:p w14:paraId="6092B0BB" w14:textId="77777777" w:rsidR="00087628" w:rsidRPr="007402EE" w:rsidRDefault="00087628" w:rsidP="00AA5F94">
            <w:pPr>
                <w:pStyle w:val="1"/>
            </w:pPr>
            <w:r w:rsidRPr="007402EE">
                <w:rPr>
                    <w:rFonts w:hint="eastAsia"/>
                </w:rPr>
                <w:t>【${detail_type}】</w:t>
            </w:r>
        </w:p>
    <#list detail_list as articleItem>
        <w:p w14:paraId="7692E01B" w14:textId="16D8DC69" w:rsidR="00087628" w:rsidRDefault="00FF5B0F" w:rsidP="00AA5F94">
            <w:pPr>
                <w:pStyle w:val="2"/>
                <w:numPr>
                    <w:ilvl w:val="0"/>
                    <w:numId w:val="2"/>
                </w:numPr>
            </w:pPr>
            <w:r>
                <w:rPr>
                    <w:rFonts w:hint="eastAsia"/>
                </w:rPr>
                <w:t>${articleItem.title!'No Title'}</w:t>
            </w:r>
        </w:p>
        <w:p w14:paraId="0CE45DD7" w14:textId="77F62375" w:rsidR="00FF5B0F" w:rsidRDefault="00FF5B0F" w:rsidP="00FF5B0F">
            <w:pPr>
                <w:ind w:firstLineChars="133" w:firstLine="426"/>
                <w:rPr>
                    <w:rFonts w:ascii="宋体" w:eastAsia="宋体" w:hAnsi="宋体"/>
                    <w:sz w:val="32"/>
                    <w:szCs w:val="32"/>
                </w:rPr>
            </w:pPr>
            <w:r>
                <w:rPr>
                    <w:rFonts w:ascii="宋体" w:eastAsia="宋体" w:hAnsi="宋体" w:hint="eastAsia"/>
                    <w:sz w:val="32"/>
                    <w:szCs w:val="32"/>
                </w:rPr>
                <w:t>${articleItem.summary!''}</w:t>
            </w:r>
        </w:p>
        <w:p w14:paraId="3ECA7B13" w14:textId="33DE1DF2" w:rsidR="00FF5B0F" w:rsidRPr="00FF5B0F" w:rsidRDefault="00FF5B0F" w:rsidP="00FF5B0F">
            <w:pPr>
                <w:ind w:firstLineChars="133" w:firstLine="427"/>
                <w:rPr>
                    <w:rFonts w:ascii="宋体" w:eastAsia="宋体" w:hAnsi="宋体" w:hint="eastAsia"/>
                    <w:b/>
                    <w:bCs/>
                    <w:sz w:val="32"/>
                    <w:szCs w:val="32"/>
                </w:rPr>
            </w:pPr>
            <w:r w:rsidRPr="00FF5B0F">
                <w:rPr>
                    <w:rFonts w:ascii="宋体" w:eastAsia="宋体" w:hAnsi="宋体" w:hint="eastAsia"/>
                    <w:b/>
                    <w:bCs/>
                    <w:sz w:val="32"/>
                    <w:szCs w:val="32"/>
                </w:rPr>
                <w:t>${articleItem.articleUrl!''}</w:t>
            </w:r>
        </w:p>
        <!-- 空行=2 -->
        <w:p w14:paraId="450ECD79" w14:textId="7E849AB6" w:rsidR="00FF5B0F" w:rsidRDefault="00FF5B0F" w:rsidP="0074439B">
            <w:pPr>
                <w:jc w:val="center"/>
                <w:rPr>
                    <w:rFonts w:ascii="宋体" w:eastAsia="宋体" w:hAnsi="宋体"/>
                </w:rPr>
            </w:pPr>
        </w:p>
        <w:p w14:paraId="40303A1E" w14:textId="77777777" w:rsidR="0074439B" w:rsidRPr="0074439B" w:rsidRDefault="0074439B" w:rsidP="0074439B">
            <w:pPr>
                <w:jc w:val="center"/>
                <w:rPr>
                    <w:rFonts w:ascii="宋体" w:eastAsia="宋体" w:hAnsi="宋体" w:hint="eastAsia"/>
                </w:rPr>
            </w:pPr>
        </w:p>
    </#list>
</#macro>