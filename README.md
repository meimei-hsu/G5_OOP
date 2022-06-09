# G5_OOP final_project

>路徑： <br>
>	Java SE11, 
>	mysql-connector-java-8.0.16

>使用說明：
>	1. 由於資料庫因素，目前程式只支援指考公民109年和108年的內容，並且複習功能暫時無法使用。<br>
>	登入後會依據不同使用者的使用情況，將記錄存取，並且不會因為關閉程式而遺失。<br>
>	進行測驗後，可以獲得作答時間、答題狀況、正解詳解、筆記內容等資料。<br>
>	2. 詳解頁面的答案邊框顏色意義：綠色代表正解、紅色是使用者選的錯誤答案、橘色是使用者選的正確答案。<br>
>	使用者答卷目錄的邊框顏色意義：綠色代表及格 (答對率大於60%)、紅色則反之，代表不及格。<br>
>	作答頁面按鈕說明："|>" 和 "||" 按鈕分別執行開始計時與暫停功能；<br>
>	&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&ensp; "。。。" 按鈕執行切換到目錄頁功能；<br>
>	&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&ensp; 燈泡按鈕讓使用者標記難題，點選後在題目目錄頁會顯示以供返回作答；<br>
>	&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&ensp; "X" 按鈕記錄使用者認為的錯誤選項，點選後對應的答案選項會從系統刪除；<br>
>	&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&ensp; "ˇ"按鈕表示作答完畢，提醒使用者是否確認送交；<br>
>	&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&ensp; 題目目錄頁的答案邊框若是橘色代表未作答，若是藍色則代表已作答。<br>

>程式目錄： <br>
>	初始頁面 - TitlePanel <br>
>	登入頁面 - LoginPanel <br>
>	註冊頁面 - SignUpPanel <br>
>	首頁頁面 - HomePanel <br>
>	選擇考試範圍 - RangePanel <br>
>	作答說明 - InstructionPanel <br>
>	作答頁面 - QuestionPanel <br>
>	作答完畢計算分數 - ToAnswerPanel <br>
>	詳解目錄 (顯示特定範圍內所有題目) - AnswerListPanel <br>
>	詳解及筆記頁面 - AnswerKeyPanel <br>
>	筆記目錄 (顯示所有科目) - SubjectPanel <br>
>	使用者答卷目錄 - ListPanel <br>
>	答案功能 (儲存資料與計算分數) - Answer <br>
>	按鈕功能 (倒數計時、開始暫停、回目錄頁按鈕、回首頁按鈕、作答完成的流程) - QTool <br>
>	進入點(創建物件、設定Frame、銷毀Panel、新增ActionListener) - Viewer <br>
>	按鈕設計 (橢圓框、圓框) - style.BubbleBorder、style.RoundButton <br>

>常見問題說明：<br>
>	1. 因版面設計問題，作答頁面中109年Q11和108年Q15須展開整個畫面大小，已顯示完整答案選項內容
>	2. 因資料庫設定問題，當畫面閒置時間過久會斷線，已設定autoconnect = true，但在重新連線時可能會延遲
>	3. 因資料不足，只能使用全新題目的108,109指考公民範圍進行測驗
>	4. 因版面設計問題，在加載按鈕時: 在作答頁面的單選題時有時會出現重複塗色問題，但其實系統接收的答案是使用者最後選擇的內容
	，當切換頁面再回去時就會顯示正常；在目錄頁面上下滑動時，按鈕顏色會超出設定範圍，造成畫面不美觀但不影響功能
>	5. 在使用者答卷目錄頁面，刪除試卷後，點選按鈕回到目錄頁面時，可能仍會出現試卷按鈕但已無法點選，該內容其實已經刪除
	，當切換頁面再回去時就會顯示正常
