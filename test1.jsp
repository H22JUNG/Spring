<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>Insert title here</title>
        <script type="text/javascript" src="js/jquery-3.6.1.min.js"></script>
    </head>

    <body>
        <h1>비동기 댓글 등록<h1>
        <h1>글 제목</h1>
        <hr />
        <p>글 내용입니다</p>
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <hr />
        <div id="comment"></div>
        <hr />
        <div id="input">
            이름 : <input type="text" id="owner" name="owner" /><br /> 내용 :
            <textarea name="content" id="content" cols="30" rows="10"></textarea>
            <button id="submit">전송</button>
        </div>

        <script type="text/javascript">
            document.getElementById("submit").addEventListener("click", function () {
                let owner = document.getElementById("owner").value;
                let content = document.getElementById("content").value;

                const simple_data = { owner, content };

                $.ajax({
                    url: "${pageContext.request.contextPath}/all",
                    type: "POST",
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    data: JSON.stringify(simple_data),
                    success: function (d) {

                        if (d.state == "ok") { //전송 되면 댓글div에 추가
                            const comdiv = document.getElementById("comment");

                            const div = document.createElement("div");
                            div.style.border = "1px solid black";
                            const h4 = document.createElement("h4");
                            h4.innerText = owner;
                            const p = document.createElement("p");
                            p.innerText = content;


                            const btn = document.createElement("button");
                            btn.innerText = "삭제";
                            //btn.setAttribute("id", "delete" + list.id);


                            div.append(h4);
                            div.append(p);
                            div.append(btn);
                            comdiv.append(div);
                        }
                    },
                    error: function (e) {
                        alert(e);
                    }
                });
            });

            window.addEventListener("DOMContentLoaded", function () { //모든 문서가 뜨면 데이터 가져와라
                $.ajax({
                    url: "${pageContext.request.contextPath}/comment",
                    type: "GET",
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    success: function (data) {		//controller에서 리턴된 json이 스크립트에서 사용할 수 있는 객체 혹은 배열로 자동변환됨
                    	console.log(data);
                        const comdiv = document.getElementById("comment");

                        for (let comment of data) {
                            const div = document.createElement("div");
                            div.style.border = "1px solid black";
                            const h4 = document.createElement("h4");
                            h4.innerText = comment.owner;
                            const p = document.createElement("p");
                            p.innerText = comment.content;


                            const btn = document.createElement("button");
                            btn.innerText = "삭제";
                            //btn.setAttribute("id", "delete" + comment.id);


                            btn.addEventListener("click", function () {
                                //let owner = document.getElementById("delete" + comment.id).parentElement.firstElementChild.innerText;
                                //console.log(owner);

                                const idObj = {id:comment.id}; //키:값 객체로 넣어주기

                                fetch("${pageContext.request.contextPath}/delete", {
                                    method: "DELETE",
                                    headers: { "Content-Type": "application/json" }, //데이터를 json타입으로 받아옴
                                    body: JSON.stringify(idObj) //직렬화 할 데이터는 반드시 객체여야함
                                }).then(response => response.json())
                                    .then(data => {
                                        btn.parentElement.remove();
                                    })
                            })
                            div.append(h4);
                            div.append(p);
                            div.append(btn);
                            comdiv.append(div);
                        }
                    },
                    error: function(e) {
                        alert(e);
                    }
                });
            });

        </script>
    </body>
</html>
