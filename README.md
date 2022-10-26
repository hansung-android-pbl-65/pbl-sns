# 고급 안드로이드 프로그래밍 PBL 프로젝트 65번팀

아래 자료를 참고하여 프로젝트 진행 바랍니다.

</br>

### Git 컨벤션

* [Git 커맨드](https://medium.com/@joongwon/git-git-%EB%AA%85%EB%A0%B9%EC%96%B4-%EC%A0%95%EB%A6%AC-c25b421ecdbd)
```java
git --help
```
간단한 설명은 [Git 커맨드](https://medium.com/@joongwon/git-git-%EB%AA%85%EB%A0%B9%EC%96%B4-%EC%A0%95%EB%A6%AC-c25b421ecdbd)를 참고 바랍니다.

</br>

* Git 커밋 방법
```java
git add -A 
git commit -am "Commit Message"
git push origin branch // ex) git push origin master, git push origin develop
```

</br>

* Git 브랜치 이동 방법
```java
git checkout branch // ex) git checkout master, git checkout develop
```

</br>

* [Git 커밋 메시지 컨벤션](https://velog.io/@archivvonjang/Git-Commit-Message-Convention)
```java
커밋 메시지를 직관적으로 확인할 수 있도록 prefix + 한글 커밋 메시지(필요하다면 영어로 작성)를 디폴트로 사용하여 주시기 바랍니다.

build 파일 수정 시 다음과 같이 커밋 메시지를 작성할 수 있습니다.
'Chore: Firebase ver.x.x.x dependency 추가'
'Chore: Firebase ver.x.x.0 to ver.x.x.1 버전 업데이트'

새로운 기능을 추가하였을 경우 다음과 같이 커밋 메시지를 작성할 수 있습니다.
'Feat: 이메일을 사용하여 회원가입하는 기능 구현'

오류 수정의 경우 다음과 같이 커밋 메시지를 작성할 수 있습니다.
'Fix: 로그인 요청 시간이 10초를 초과할 경우 정상 종료되지 않던 문제 수정 #ISSUE-NUMBER'
```
보다 자세한 사항은 [Git 커밋 메시지 컨벤션](https://velog.io/@archivvonjang/Git-Commit-Message-Convention)을 참고 바랍니다.

</br>

* [Git Workflow](https://gmlwjd9405.github.io/2018/05/11/types-of-git-branch.html)
```java
작업 진행은 다음과 같은 방식으로 진행합니다.
1. 작업을 시작하려면 -> develop 브랜치 에서 feature 브랜치로 작업 브랜치 생성 -> feature 브랜치 에서 작업
2. 작업을 완료하였다면 -> feature 브랜치에서 develop 브랜치로 pr 생성 -> 코드 리뷰 -> 이상 없으면 merge
3. apk 빌드가 필요하다면 -> develop 브랜치에서 master 브랜치로 merge 후 release 생성 (테스트는 develop 브랜치 위에서 개별 환경으로 진행)
```
보다 자세한 사항은 [Git 워크플로](https://gmlwjd9405.github.io/2018/05/11/types-of-git-branch.html)를 참고 바랍니다.

</br>

### 라이브러리

* Firebase 사용은 [Firebase 사용 가이드](https://firebase.google.com/docs/reference/android/packages?hl=ko)를 참고 바랍니다.
* 텍스트 에디터는 [WYSIWYG 에디터](https://github.com/onecode369/WYSIWYG)를 참고 바랍니다.
