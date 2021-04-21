package com.example.core.domain.session

/*
LOGIN : FirebaseAuth에 currentUser가 있는 상태. 토큰 검사 -> 사용 가능하다면 메인으로 불가능하다면 리프레시 시도 -> 리프레시가 실패한다면 RE_LOGIN 전달
LOGOUT : FirebaseAuth에 currentUser가 없는 상태. 로그인 화면으로 렌더링
RE_LOGIN : currentUser로 다시 토큰을 얻어와 로그인해야하는 상태.
 */

enum class SessionState {
    LOGIN, LOGOUT, RE_LOGIN
}