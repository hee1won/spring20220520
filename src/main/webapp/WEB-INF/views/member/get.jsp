<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
	integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.1.3/css/bootstrap.min.css"
	integrity="sha512-GQGU0fMMi238uA+a/bdWJfpUGKUkBdgfFdgBm72SUQ6BeyWjoY/ton0tEjH+OSH9iP4Dfh+7HM0I9f5eR0L/4w=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"
	referrerpolicy="no-referrer"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
<title>Insert title here</title>
<script>
	$(document).ready(function(){
		
		let pwOk = false;
		let emailOk = false;
		let nicknameOk = false;
		
		// 기존 이메일
		const oldEmail = $("#email").val()
		
		// 이메일 중복확인 버튼 활성화 
		$("#email").keyup(function(){
			const newEmail = $("#email").val()
			
			if(oldEmail == newEmail){
				$("#emailCheckButton").attr("disabled","")
				$("#emailCheck").text("");
			} else{
				$("#emailCheckButton").removeAttr("disabled")
			}
		})
		
		// 이메일 중복확인 버튼 눌렀을 때 발생하는 코드 
		$("#emailCheckButton").click(function(e){
			e.preventDefault();
			
			$(this).attr("disabled","");
			const data = {
					email : $("#email").val()
			};
			
			emailOk = false;
			$.ajax({
				
				url : "${appRoot}/member/get",
				data : data,
				success : function(data){
					switch(data) {
					case "ok" :
						$("#emailCheck").text("사용 가능한 이메일 입니다.")
						emailOk = true;
						break;
					case "notok" :
						$("#emailCheck").text("사용중인 이메일 입니다.")
					}
				},
				
				error : function(){
					$("#emailCheck").text("이메일 확인중 오류 발생.")
				},
				complete : function(){
					$("#emailCheckButton").removeAttr("disabled", "")
					enableSubmit()
				}
			})
			
		})
	
		// 기존 닉네임
		const oldnickname = $("#email").val()
		
		// 닉네임 중복확인 버튼 활성화 
		$("#nickname").keyup(function(){
			const newnickname = $("#nickname").val()
			
			if(oldnickname == newnickname){
				$("#nicknameCheckButton").attr("disabled","")
				$("#nicknameCheck").text("");
			} else{
				$("#nicknameCheckButton").removeAttr("disabled")
			}
		})

		// 닉네임 중복확인 버튼 눌렀을 때 발생하는 코드 
		$("#nicknameCheckButton").click(function(e){
			e.preventDefault();
			$(this).attr("disabled", "");
			const data = {
				nickname : $("#nickname").val()
			};
			nicknameOk = false;
			$.ajax({
			
				url : "${appRoot}/member/get",
				data :data,
				
				success : function(data){
					switch(data){
					case "ok" :
						$("#nicknameCheck").text("사용 가능한 닉네임 입니다.")
						nicknameOk = true;
						break;
					case "notok" :
						$("#nicknameCheck").text("사용중인 닉네임 입니다.")
					}
				},
				error : function(){
					$("#nicknameCheck").text("확인중 오류 발생")
				},
				complete : function(){
					$("#nicknameCheckButton").removeAttr("disabled", "")
					enableSubmit()
				}
			})
		})
		
		// 패스워드 일치 확인하는 코드 
		$("#pw1, #pw2").keyup(function(){
			const pw1 = $("#pw1").val()
			const pw2 = $("#pw2").val()
			
			pwOk = false;
			if(pw1 == pw2){
				$("#passwordCheck").text("패스워드가 일치합니다.")
				pwOk = true;
			} else {
				$("#passwordCheck").text("패스워드가 일치하지않습니다.")
			}
			
			enableSubmit()
		})
		
		// 수정 버튼 활성화 코드 
		const enableSubmit = function () {
			if (pwOk || emailOk || nicknameOk) {
				$("#modifyButton").removeAttr("disabled");
			} else {
				$("#modifyButton").attr("disabled", "");
			}
		}
		
		// 수정 submit 버튼 modifySubmitButton2 클릭 시 
		$("#modifySubmitButton2").click(function(e){
			e.preventDefault()
			const form3 = $("#form3")
			
			// input 값 옮기기 
			form3.find("[name=password]").val($("#pw1").val())
			form3.find("[name=email]").val($("#email").val())
			form3.find("[name=nickname]").val($("#nickname").val())
			
			// submit()
			form3.submit()
		})
		
	})
	
</script>
</head>
<body>
	<my:navBar></my:navBar>
	

<form action="${appRoot}/member/get" id="form2">
	<div>
		아이디 :
		<input type="text" value="${member.id }" readonly/>
		<br />
		패스워드 :
		<input type="text" value="" id = "pw1" /> 
		<br />
		패스워드 확인 :
		<input type="text" value="" id = "pw2" />
		<p id="passwordCheck"></p>
		<br />
		이메일 :
		<input type="email" value="${member.email }" id="email"/> 
		<button id="emailCheckButton" disabled>이메일 중복확인</button>
		<p id="emailCheck"></p>
		<br />
		닉네임 :
		<input type="text" value="${member.nickname }" id="nickname"/> 
		<button id="nicknameCheckButton" disabled>닉네임 중복확인</button>
		<p id="nicknameCheck"></p>
		<br />
		가입일시 :
		<input type="datetime-local" value="${member.inserted}" readonly />
		<br />
	</div>
</form>
	
	<%-- 요구사항 --%>
	<%-- 1. 이메일 input에 변경 발생시 '이메일 중복확인버튼 활성화' -> 버튼클릭시 ajax로 요청 / 응답, 적절한 메세지 출력 --%>
	<%-- 2. 닉네임 input에 변경 발생시 '닉네임 중복확인버튼 활성화' --%>
	<%-- 3. 암호 / 암호 확인일치, 이메일 중복확인 완료, 닉네임 중복확인 완료 시에만 수정버튼 활성화 --%>

	<div>
		<button disabled id="modifyButton" data-bs-toggle="modal" data-bs-target="#modal2">수정</button>
		<button data-bs-toggle="modal" data-bs-target="#modal1">삭제</button>
	</div>
	
	<!-- 탈퇴 암호 확인 -->
	<div class="modal fade" id="modal1" tabindex="-1"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
				<form id="form1" action="${appRoot }/member/remove" method="post">
					<input type="hidden" value="${member.id }" name="id" />
					암호 : <input type="text" name="password"/>
				</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Close</button>
					<button form="form1" type="submit" class="btn btn-danger">탈퇴</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 수정 (기존) 암호 확인 -->
	<div class="modal fade" id="modal2" tabindex="-1"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel2">Modal title</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
				<form id="form3" action="${appRoot }/member/modify" method="post">
					<input type="hidden" value="${member.id }" name="id" />
					<input type="hidden" name="password" />
					<input type="hidden" name="email" />
					<input type="hidden" name="nickname" />
					기존 암호 : <input type="text" name="oldPassword"/>
				</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Close</button>
					<button id="modifySubmitButton2" form="form3" type="submit" class="btn btn-primary">수정</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>