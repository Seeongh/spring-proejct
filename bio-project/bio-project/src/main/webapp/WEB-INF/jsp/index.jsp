<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html lang="en">
<head>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Gowun+Dodum&display=swap" rel="stylesheet">

    <meta charset="UTF-8">
    <title>hellow</title>
</head>
<style>
</style>
<body style="background-color:rgb(232,241,255)">
<!--rgb(E8,FF,F1)--!>
<div id="root" class="bioWrap">
    <div class="App" style>
        <div id="BioHead" class="header_frag">
            <div class="inner_sidebar">
                <div class="sidebar_box">
                    <div class="sidebar_index">
                      <button type="button" style="border:none">
                      <img class = "sidebaricon" , src="../images/main/icon_home.png" alt="">
                      </button>
                    </div>
                    <div class="sidebar_index">
                      <img src="../images/main/icon_login_user.png" alt="", onclick=yet()>
                    </div>
                    <div class="sidebar_index">
                      <img class = "sidebaricon" , src="../images/main/icon_login_user.png" alt="", onclick=yet()>
                    </div>
                    <div class="sidebar_index">
                      <img class = "sidebaricon" , src="../images/main/icon_login_user.png" alt="", onclick=yet()>
                    </div>
                </div>
           </div>
        </div>
        <main id="BioInfo">
            <main id="BioInfoContent" class="bioInfoContent">
                <div id="Logo">
                    <div class="logo" style =" font-family: 'Black Han Sans';">
                        <h2>    MZ HealthCare   </h2>
                    </div>
                    <div class="notice">
                        <div class="search"> </div>
                    </div>
               </div>
                <div id="Bio-graph">
                    <div class="graph-rhythm">
                        <div class="biorhythm_content">
                        </div>
                    </div>

                    <div class="graph-rhythm">
                        <div class="biorhythm_content">
                        </div>
                    </div>
                </div>
                <div id="Banner">
                    <div class="banner-content">
                    </div>
                </div>

                <div id="Bio_list">
                    <div class="user-list">
                    </div>
                </div>
            </main>
            <main id="BioMyContent" class="bioMyContent">
                <div id="Information">
                    <div class="myProfile">
                         <button type="button" class="login_box__form_btn" id="btn_login"
                         style="margin-left: 7vw;font-family: 'Black Han Sans', sans-serif;border:none;background-color:rgb(232,241,255);"
                         onclick="duplicatelogin()">로그인 해주세요</button>
                         <img src="../images/main/icon_login_user.png" alt="" style = "width:2vw;height:3vh;">
                    </div>
                </div>
                <div id="Targetinfo">
                    <div class="graph-rhythm">
                        <div class="biorhythm_content">
                        </div>
                    </div>
                </div>
                <div id="GradePoint">
                    <div class="myGrade">
                    </div>
                </div>
            </main>
        </main>
    </div>
</div>
<link rel="stylesheet" href="../css/main.css" type="text/css">
<script>

function yet(){alert("아직 쓸 수 없습니다.");}
// 중복 로그인 방지
	function duplicatelogin(){
		if(!!getCookie('acToken')){	//로그인 되어있을때
			location.href = '/';
		}else{
			$.ajax({
		           type:"Post",
		           url:"/user/loginCheck",
		           dataType:"JSON", // 옵션이므로 JSON으로 받을게 아니면 안써도 됨
		           data: {
// 		        	   "userRoleFk": $('select[name="userRoleFk"]').val(),
		        	   "userId": $('input[id="userId"]').val(),
		        	   "userPwEnc": $('input[id="userPwEnc"]').val()
		           },
		           success : function(data) {
		               if(data.resultCode == '0000'){
					   		location.href = '/';
		               }else{
			        	   alert('아이디 또는 비밀번호를 잘못 입력했습니다.\n입력하신 내용을 다시 확인해주세요.')
			           }
		           },
		           complete : function(data) {
		                 // 통신이 실패했어도 완료가 되었을 때 이 함수를 타게 된다.

		           },
		           error : function(xhr, status, error) {
		                console.log(error)
		           }
		     });
// 			$('#command').submit();
		}
	}


</script>
</body>
</html>