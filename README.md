# MySpringWeb

+ Excel Export
+ https://github.com/rainabba/jquery-table2excel/issues/105
+ http://localhost:8080/dart/docs?stock_code=005930&corp_code=00126380&bsns_year=2021&reprt_code=11011&fs_div=CFS

```shell
$ mysql.server start
$ mysql.server stop
```

| 요청키         | 명칭      | 타입         | 필수여부 | 값설명                                                                |
| ----------- | ------- | ---------- | ---- | ------------------------------------------------------------------ |
| crtfc\_key  | API 인증키 | STRING(40) | Y    | 발급받은 인증키(40자리)                                                     |
| corp\_code  | 고유번호    | STRING(8)  | Y    | 공시대상회사의 고유번호(8자리)<br>※ 개발가이드 > 공시정보 > 고유번호 참고                      |
| bsns\_year  | 사업연도    | STRING(4)  | Y    | 사업연도(4자리) ※ 2015년 이후 부터 정보제공                                       |
| reprt\_code | 보고서 코드  | STRING(5)  | Y    | 1분기보고서 : 11013<br>반기보고서 : 11012<br>3분기보고서 : 11014<br>사업보고서 : 11011 |
| fs\_div     | 개별/연결구분 | STRING(3)  | Y    | CFS:연결재무제표, OFS:재무제표                                               |

| rcept\_no           | 접수번호       | 접수번호(14자리)<br><br>※ 공시뷰어 연결에 이용예시<br>\- PC용 : https://dart.fss.or.kr/dsaf001/main.do?rcpNo=접수번호        |
| ------------------- | ---------- | ------------------------------------------------------------------------------------------------------ |
| reprt\_code         | 보고서 코드     | 1분기보고서 : 11013 반기보고서 : 11012 3분기보고서 : 11014 사업보고서 : 11011                                              |
| bsns\_year          | 사업 연도      | 2018                                                                                                   |
| corp\_code          | 고유번호       | 공시대상회사의 고유번호(8자리)                                                                                      |
| sj\_div             | 재무제표구분     | BS : 재무상태표 IS : 손익계산서 CIS : 포괄손익계산서 CF : 현금흐름표 SCE : 자본변동표                                             |
| sj\_nm              | 재무제표명      | ex) 재무상태표 또는 손익계산서 출력                                                                                  |
| account\_id         | 계정ID       | XBRL 표준계정ID ※ 표준계정ID가 아닐경우 ""-표준계정코드 미사용-"" 표시                                                         |
| account\_nm         | 계정명        | 계정명칭 ex) 자본총계                                                                                          |
| account\_detail     | 계정상세       | ※ 자본변동표에만 출력 ex) 계정 상세명칭 예시 - 자본 \[member\]|지배기업 소유주지분 - 자본 \[member\]|지배기업 소유주지분|기타포괄손익누계액 \[member\] |
| thstrm\_nm          | 당기명        | ex) 제 13 기                                                                                             |
| thstrm\_amount      | 당기금액       | 9,999,999,999 ※ 분/반기 보고서이면서 (포괄)손익계산서 일 경우 \[3개월\] 금액                                                  |
| thstrm\_add\_amount | 당기누적금액     | 9,999,999,999                                                                                          |
| frmtrm\_nm          | 전기명        | ex) 제 12 기말                                                                                            |
| frmtrm\_amount      | 전기금액       | 9,999,999,999                                                                                          |
| frmtrm\_q\_nm       | 전기명(분/반기)  | ex) 제 18 기 반기                                                                                          |
| frmtrm\_q\_amount   | 전기금액(분/반기) | 9,999,999,999 ※ 분/반기 보고서이면서 (포괄)손익계산서 일 경우 \[3개월\] 금액                                                  |
| frmtrm\_add\_amount | 전기누적금액     | 9,999,999,999                                                                                          |
| bfefrmtrm\_nm       | 전전기명       | ex) 제 11 기말(※ 사업보고서의 경우에만 출력)                                                                          |
| bfefrmtrm\_amount   | 전전기금액      | 9,999,999,999(※ 사업보고서의 경우에만 출력)                                                                        |
| ord                 | 계정과목 정렬순서  | 계정과목 정렬순서                                                                                              |
| currency            | 통화 단위      | 통화 단위                                                                                                  |