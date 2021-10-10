/*     */ package sun.security.tools.keytool;
/*     */ 
/*     */ import java.util.ListResourceBundle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Resources_ko
/*     */   extends ListResourceBundle
/*     */ {
/*  35 */   private static final Object[][] contents = new Object[][] { { "NEWLINE", "\n" }, { "STAR", "*******************************************" }, { "STARNN", "*******************************************\n\n" }, { ".OPTION.", " [OPTION]..." }, { "Options.", "옵션:" }, { "Use.keytool.help.for.all.available.commands", "사용 가능한 모든 명령에 \"keytool -help\" 사용" }, { "Key.and.Certificate.Management.Tool", "키 및 인증서 관리 툴" }, { "Commands.", "명령:" }, { "Use.keytool.command.name.help.for.usage.of.command.name", "command_name 사용법에 \"keytool -command_name -help\" 사용" }, { "Generates.a.certificate.request", "인증서 요청을 생성합니다." }, { "Changes.an.entry.s.alias", "항목의 별칭을 변경합니다." }, { "Deletes.an.entry", "항목을 삭제합니다." }, { "Exports.certificate", "인증서를 익스포트합니다." }, { "Generates.a.key.pair", "키 쌍을 생성합니다." }, { "Generates.a.secret.key", "보안 키를 생성합니다." }, { "Generates.certificate.from.a.certificate.request", "인증서 요청에서 인증서를 생성합니다." }, { "Generates.CRL", "CRL을 생성합니다." }, { "Generated.keyAlgName.secret.key", "{0} 보안 키를 생성합니다." }, { "Generated.keysize.bit.keyAlgName.secret.key", "{0}비트 {1} 보안 키를 생성합니다." }, { "Imports.entries.from.a.JDK.1.1.x.style.identity.database", "JDK 1.1.x 스타일 ID 데이터베이스에서 항목을 임포트합니다." }, { "Imports.a.certificate.or.a.certificate.chain", "인증서 또는 인증서 체인을 임포트합니다." }, { "Imports.a.password", "비밀번호를 임포트합니다." }, { "Imports.one.or.all.entries.from.another.keystore", "다른 키 저장소에서 하나 또는 모든 항목을 임포트합니다." }, { "Clones.a.key.entry", "키 항목을 복제합니다." }, { "Changes.the.key.password.of.an.entry", "항목의 키 비밀번호를 변경합니다." }, { "Lists.entries.in.a.keystore", "키 저장소의 항목을 나열합니다." }, { "Prints.the.content.of.a.certificate", "인증서의 콘텐츠를 인쇄합니다." }, { "Prints.the.content.of.a.certificate.request", "인증서 요청의 콘텐츠를 인쇄합니다." }, { "Prints.the.content.of.a.CRL.file", "CRL 파일의 콘텐츠를 인쇄합니다." }, { "Generates.a.self.signed.certificate", "자체 서명된 인증서를 생성합니다." }, { "Changes.the.store.password.of.a.keystore", "키 저장소의 저장소 비밀번호를 변경합니다." }, { "alias.name.of.the.entry.to.process", "처리할 항목의 별칭 이름" }, { "destination.alias", "대상 별칭" }, { "destination.key.password", "대상 키 비밀번호" }, { "destination.keystore.name", "대상 키 저장소 이름" }, { "destination.keystore.password.protected", "대상 키 저장소 비밀번호로 보호됨" }, { "destination.keystore.provider.name", "대상 키 저장소 제공자 이름" }, { "destination.keystore.password", "대상 키 저장소 비밀번호" }, { "destination.keystore.type", "대상 키 저장소 유형" }, { "distinguished.name", "식별 이름" }, { "X.509.extension", "X.509 확장" }, { "output.file.name", "출력 파일 이름" }, { "input.file.name", "입력 파일 이름" }, { "key.algorithm.name", "키 알고리즘 이름" }, { "key.password", "키 비밀번호" }, { "key.bit.size", "키 비트 크기" }, { "keystore.name", "키 저장소 이름" }, { "new.password", "새 비밀번호" }, { "do.not.prompt", "확인하지 않음" }, { "password.through.protected.mechanism", "보호되는 메커니즘을 통한 비밀번호" }, { "provider.argument", "제공자 인수" }, { "provider.class.name", "제공자 클래스 이름" }, { "provider.name", "제공자 이름" }, { "provider.classpath", "제공자 클래스 경로" }, { "output.in.RFC.style", "RFC 스타일의 출력" }, { "signature.algorithm.name", "서명 알고리즘 이름" }, { "source.alias", "소스 별칭" }, { "source.key.password", "소스 키 비밀번호" }, { "source.keystore.name", "소스 키 저장소 이름" }, { "source.keystore.password.protected", "소스 키 저장소 비밀번호로 보호됨" }, { "source.keystore.provider.name", "소스 키 저장소 제공자 이름" }, { "source.keystore.password", "소스 키 저장소 비밀번호" }, { "source.keystore.type", "소스 키 저장소 유형" }, { "SSL.server.host.and.port", "SSL 서버 호스트 및 포트" }, { "signed.jar.file", "서명된 jar 파일" }, { "certificate.validity.start.date.time", "인증서 유효 기간 시작 날짜/시간" }, { "keystore.password", "키 저장소 비밀번호" }, { "keystore.type", "키 저장소 유형" }, { "trust.certificates.from.cacerts", "cacerts의 보안 인증서" }, { "verbose.output", "상세 정보 출력" }, { "validity.number.of.days", "유효 기간 일 수" }, { "Serial.ID.of.cert.to.revoke", "철회할 인증서의 일련 ID" }, { "keytool.error.", "keytool 오류: " }, { "Illegal.option.", "잘못된 옵션:  " }, { "Illegal.value.", "잘못된 값: " }, { "Unknown.password.type.", "알 수 없는 비밀번호 유형: " }, { "Cannot.find.environment.variable.", "환경 변수를 찾을 수 없음: " }, { "Cannot.find.file.", "파일을 찾을 수 없음: " }, { "Command.option.flag.needs.an.argument.", "명령 옵션 {0}에 인수가 필요합니다." }, { "Warning.Different.store.and.key.passwords.not.supported.for.PKCS12.KeyStores.Ignoring.user.specified.command.value.", "경고: 다른 저장소 및 키 비밀번호는 PKCS12 KeyStores에 대해 지원되지 않습니다. 사용자가 지정한 {0} 값을 무시하는 중입니다." }, { ".keystore.must.be.NONE.if.storetype.is.{0}", "-storetype이 {0}인 경우 -keystore는 NONE이어야 합니다." }, { "Too.many.retries.program.terminated", "재시도 횟수가 너무 많아 프로그램이 종료되었습니다." }, { ".storepasswd.and.keypasswd.commands.not.supported.if.storetype.is.{0}", "-storetype이 {0}인 경우 -storepasswd 및 -keypasswd 명령이 지원되지 않습니다." }, { ".keypasswd.commands.not.supported.if.storetype.is.PKCS12", "-storetype이 PKCS12인 경우 -keypasswd 명령이 지원되지 않습니다." }, { ".keypass.and.new.can.not.be.specified.if.storetype.is.{0}", "-storetype이 {0}인 경우 -keypass 및 -new를 지정할 수 없습니다." }, { "if.protected.is.specified.then.storepass.keypass.and.new.must.not.be.specified", "-protected를 지정한 경우 -storepass, -keypass 및 -new를 지정하지 않아야 합니다." }, { "if.srcprotected.is.specified.then.srcstorepass.and.srckeypass.must.not.be.specified", "-srcprotected를 지정한 경우 -srcstorepass 및 -srckeypass를 지정하지 않아야 합니다." }, { "if.keystore.is.not.password.protected.then.storepass.keypass.and.new.must.not.be.specified", "키 저장소가 비밀번호로 보호되지 않는 경우 -storepass, -keypass 및 -new를 지정하지 않아야 합니다." }, { "if.source.keystore.is.not.password.protected.then.srcstorepass.and.srckeypass.must.not.be.specified", "소스 키 저장소가 비밀번호로 보호되지 않는 경우 -srcstorepass 및 -srckeypass를 지정하지 않아야 합니다." }, { "Illegal.startdate.value", "startdate 값이 잘못되었습니다." }, { "Validity.must.be.greater.than.zero", "유효 기간은 0보다 커야 합니다." }, { "provName.not.a.provider", "{0}은(는) 제공자가 아닙니다." }, { "Usage.error.no.command.provided", "사용법 오류: 명령을 입력하지 않았습니다." }, { "Source.keystore.file.exists.but.is.empty.", "소스 키 저장소 파일이 존재하지만 비어 있음: " }, { "Please.specify.srckeystore", "-srckeystore를 지정하십시오." }, { "Must.not.specify.both.v.and.rfc.with.list.command", "'list' 명령에 -v와 -rfc를 함께 지정하지 않아야 합니다." }, { "Key.password.must.be.at.least.6.characters", "키 비밀번호는 6자 이상이어야 합니다." }, { "New.password.must.be.at.least.6.characters", "새 비밀번호는 6자 이상이어야 합니다." }, { "Keystore.file.exists.but.is.empty.", "키 저장소 파일이 존재하지만 비어 있음: " }, { "Keystore.file.does.not.exist.", "키 저장소 파일이 존재하지 않음: " }, { "Must.specify.destination.alias", "대상 별칭을 지정해야 합니다." }, { "Must.specify.alias", "별칭을 지정해야 합니다." }, { "Keystore.password.must.be.at.least.6.characters", "키 저장소 비밀번호는 6자 이상이어야 합니다." }, { "Enter.the.password.to.be.stored.", "저장할 비밀번호 입력:  " }, { "Enter.keystore.password.", "키 저장소 비밀번호 입력:  " }, { "Enter.source.keystore.password.", "소스 키 저장소 비밀번호 입력:  " }, { "Enter.destination.keystore.password.", "대상 키 저장소 비밀번호 입력:  " }, { "Keystore.password.is.too.short.must.be.at.least.6.characters", "키 저장소 비밀번호가 너무 짧음 - 6자 이상이어야 합니다." }, { "Unknown.Entry.Type", "알 수 없는 항목 유형" }, { "Too.many.failures.Alias.not.changed", "오류가 너무 많습니다. 별칭이 변경되지 않았습니다." }, { "Entry.for.alias.alias.successfully.imported.", "{0} 별칭에 대한 항목이 성공적으로 임포트되었습니다." }, { "Entry.for.alias.alias.not.imported.", "{0} 별칭에 대한 항목이 임포트되지 않았습니다." }, { "Problem.importing.entry.for.alias.alias.exception.Entry.for.alias.alias.not.imported.", "{0} 별칭에 대한 항목을 임포트하는 중 문제 발생: {1}.\n{0} 별칭에 대한 항목이 임포트되지 않았습니다." }, { "Import.command.completed.ok.entries.successfully.imported.fail.entries.failed.or.cancelled", "임포트 명령 완료: 성공적으로 임포트된 항목은 {0}개, 실패하거나 취소된 항목은 {1}개입니다." }, { "Warning.Overwriting.existing.alias.alias.in.destination.keystore", "경고: 대상 키 저장소에서 기존 별칭 {0}을(를) 겹쳐 쓰는 중" }, { "Existing.entry.alias.alias.exists.overwrite.no.", "기존 항목 별칭 {0}이(가) 존재합니다. 겹쳐 쓰겠습니까? [아니오]:  " }, { "Too.many.failures.try.later", "오류가 너무 많음 - 나중에 시도하십시오." }, { "Certification.request.stored.in.file.filename.", "인증 요청이 <{0}> 파일에 저장되었습니다." }, { "Submit.this.to.your.CA", "CA에게 제출하십시오." }, { "if.alias.not.specified.destalias.and.srckeypass.must.not.be.specified", "별칭을 지정하지 않은 경우 destalias 및 srckeypass를 지정하지 않아야 합니다." }, { "The.destination.pkcs12.keystore.has.different.storepass.and.keypass.Please.retry.with.destkeypass.specified.", "대상 pkcs12 키 저장소에 다른 storepass 및 keypass가 있습니다. 지정된 -destkeypass로 재시도하십시오." }, { "Certificate.stored.in.file.filename.", "인증서가 <{0}> 파일에 저장되었습니다." }, { "Certificate.reply.was.installed.in.keystore", "인증서 회신이 키 저장소에 설치되었습니다." }, { "Certificate.reply.was.not.installed.in.keystore", "인증서 회신이 키 저장소에 설치되지 않았습니다." }, { "Certificate.was.added.to.keystore", "인증서가 키 저장소에 추가되었습니다." }, { "Certificate.was.not.added.to.keystore", "인증서가 키 저장소에 추가되지 않았습니다." }, { ".Storing.ksfname.", "[{0}을(를) 저장하는 중]" }, { "alias.has.no.public.key.certificate.", "{0}에 공용 키(인증서)가 없습니다." }, { "Cannot.derive.signature.algorithm", "서명 알고리즘을 파생할 수 없습니다." }, { "Alias.alias.does.not.exist", "<{0}> 별칭이 존재하지 않습니다." }, { "Alias.alias.has.no.certificate", "<{0}> 별칭에 인증서가 없습니다." }, { "Key.pair.not.generated.alias.alias.already.exists", "키 쌍이 생성되지 않았으며 <{0}> 별칭이 존재합니다." }, { "Generating.keysize.bit.keyAlgName.key.pair.and.self.signed.certificate.sigAlgName.with.a.validity.of.validality.days.for", "다음에 대해 유효 기간이 {3}일인 {0}비트 {1} 키 쌍 및 자체 서명된 인증서({2})를 생성하는 중\n\t: {4}" }, { "Enter.key.password.for.alias.", "<{0}>에 대한 키 비밀번호를 입력하십시오." }, { ".RETURN.if.same.as.keystore.password.", "\t(키 저장소 비밀번호와 동일한 경우 Enter 키를 누름):  " }, { "Key.password.is.too.short.must.be.at.least.6.characters", "키 비밀번호가 너무 짧음 - 6자 이상이어야 합니다." }, { "Too.many.failures.key.not.added.to.keystore", "오류가 너무 많음 - 키 저장소에 키가 추가되지 않았습니다." }, { "Destination.alias.dest.already.exists", "대상 별칭 <{0}>이(가) 존재합니다." }, { "Password.is.too.short.must.be.at.least.6.characters", "비밀번호가 너무 짧음 - 6자 이상이어야 합니다." }, { "Too.many.failures.Key.entry.not.cloned", "오류가 너무 많습니다. 키 항목이 복제되지 않았습니다." }, { "key.password.for.alias.", "<{0}>에 대한 키 비밀번호" }, { "Keystore.entry.for.id.getName.already.exists", "<{0}>에 대한 키 저장소 항목이 존재합니다." }, { "Creating.keystore.entry.for.id.getName.", "<{0}>에 대한 키 저장소 항목을 생성하는 중..." }, { "No.entries.from.identity.database.added", "ID 데이터베이스에서 추가된 항목이 없습니다." }, { "Alias.name.alias", "별칭 이름: {0}" }, { "Creation.date.keyStore.getCreationDate.alias.", "생성 날짜: {0,date}" }, { "alias.keyStore.getCreationDate.alias.", "{0}, {1,date}, " }, { "alias.", "{0}, " }, { "Entry.type.type.", "항목 유형: {0}" }, { "Certificate.chain.length.", "인증서 체인 길이: " }, { "Certificate.i.1.", "인증서[{0,number,integer}]:" }, { "Certificate.fingerprint.SHA1.", "인증서 지문(SHA1): " }, { "Keystore.type.", "키 저장소 유형: " }, { "Keystore.provider.", "키 저장소 제공자: " }, { "Your.keystore.contains.keyStore.size.entry", "키 저장소에 {0,number,integer}개의 항목이 포함되어 있습니다." }, { "Your.keystore.contains.keyStore.size.entries", "키 저장소에 {0,number,integer}개의 항목이 포함되어 있습니다." }, { "Failed.to.parse.input", "입력값의 구문 분석을 실패했습니다." }, { "Empty.input", "입력값이 비어 있습니다." }, { "Not.X.509.certificate", "X.509 인증서가 아닙니다." }, { "alias.has.no.public.key", "{0}에 공용 키가 없습니다." }, { "alias.has.no.X.509.certificate", "{0}에 X.509 인증서가 없습니다." }, { "New.certificate.self.signed.", "새 인증서(자체 서명):" }, { "Reply.has.no.certificates", "회신에 인증서가 없습니다." }, { "Certificate.not.imported.alias.alias.already.exists", "인증서가 임포트되지 않았으며 <{0}> 별칭이 존재합니다." }, { "Input.not.an.X.509.certificate", "입력이 X.509 인증서가 아닙니다." }, { "Certificate.already.exists.in.keystore.under.alias.trustalias.", "인증서가 <{0}> 별칭 아래의 키 저장소에 존재합니다." }, { "Do.you.still.want.to.add.it.no.", "추가하겠습니까? [아니오]:  " }, { "Certificate.already.exists.in.system.wide.CA.keystore.under.alias.trustalias.", "인증서가 <{0}> 별칭 아래에 있는 시스템 차원의 CA 키 저장소에 존재합니다." }, { "Do.you.still.want.to.add.it.to.your.own.keystore.no.", "고유한 키 저장소에 추가하겠습니까? [아니오]:  " }, { "Trust.this.certificate.no.", "이 인증서를 신뢰합니까? [아니오]:  " }, { "YES", "예" }, { "New.prompt.", "새 {0}: " }, { "Passwords.must.differ", "비밀번호는 달라야 합니다." }, { "Re.enter.new.prompt.", "새 {0} 다시 입력: " }, { "Re.enter.password.", "비밀번호  다시 입력: " }, { "Re.enter.new.password.", "새 비밀번호 다시 입력: " }, { "They.don.t.match.Try.again", "일치하지 않습니다. 다시 시도하십시오." }, { "Enter.prompt.alias.name.", "{0} 별칭 이름 입력:  " }, { "Enter.new.alias.name.RETURN.to.cancel.import.for.this.entry.", "새 별칭 이름 입력\t(이 항목에 대한 임포트를 취소하려면 Enter 키를 누름):  " }, { "Enter.alias.name.", "별칭 이름 입력:  " }, { ".RETURN.if.same.as.for.otherAlias.", "\t(<{0}>과(와) 동일한 경우 Enter 키를 누름)" }, { "What.is.your.first.and.last.name.", "이름과 성을 입력하십시오." }, { "What.is.the.name.of.your.organizational.unit.", "조직 단위 이름을 입력하십시오." }, { "What.is.the.name.of.your.organization.", "조직 이름을 입력하십시오." }, { "What.is.the.name.of.your.City.or.Locality.", "구/군/시 이름을 입력하십시오?" }, { "What.is.the.name.of.your.State.or.Province.", "시/도 이름을 입력하십시오." }, { "What.is.the.two.letter.country.code.for.this.unit.", "이 조직의 두 자리 국가 코드를 입력하십시오." }, { "Is.name.correct.", "{0}이(가) 맞습니까?" }, { "no", "아니오" }, { "yes", "예" }, { "y", "y" }, { ".defaultValue.", "  [{0}]:  " }, { "Alias.alias.has.no.key", "<{0}> 별칭에 키가 없습니다." }, { "Alias.alias.references.an.entry.type.that.is.not.a.private.key.entry.The.keyclone.command.only.supports.cloning.of.private.key", "<{0}> 별칭은 전용 키 항목이 아닌 항목 유형을 참조합니다. -keyclone 명령은 전용 키 항목의 복제만 지원합니다." }, { ".WARNING.WARNING.WARNING.", "*****************  WARNING WARNING WARNING  *****************" }, { "Signer.d.", "서명자 #%d:" }, { "Timestamp.", "시간 기록:" }, { "Signature.", "서명:" }, { "CRLs.", "CRL:" }, { "Certificate.owner.", "인증서 소유자: " }, { "Not.a.signed.jar.file", "서명된 jar 파일이 아닙니다." }, { "No.certificate.from.the.SSL.server", "SSL 서버에서 가져온 인증서가 없습니다." }, { ".The.integrity.of.the.information.stored.in.your.keystore.", "* 키 저장소에 저장된 정보의 무결성이  *\n* 확인되지 않았습니다! 무결성을 확인하려면, *\n* 키 저장소 비밀번호를 제공해야 합니다.                  *" }, { ".The.integrity.of.the.information.stored.in.the.srckeystore.", "* srckeystore에 저장된 정보의 무결성이  *\n* 확인되지 않았습니다! 무결성을 확인하려면, *\n* srckeystore 비밀번호를 제공해야 합니다.                  *" }, { "Certificate.reply.does.not.contain.public.key.for.alias.", "인증서 회신에 <{0}>에 대한 공용 키가 포함되어 있지 않습니다." }, { "Incomplete.certificate.chain.in.reply", "회신에 불완전한 인증서 체인이 있습니다." }, { "Certificate.chain.in.reply.does.not.verify.", "회신의 인증서 체인이 확인되지 않음: " }, { "Top.level.certificate.in.reply.", "회신에 최상위 레벨 인증서가 있음:\n" }, { ".is.not.trusted.", "...을(를) 신뢰할 수 없습니다. " }, { "Install.reply.anyway.no.", "회신을 설치하겠습니까? [아니오]:  " }, { "NO", "아니오" }, { "Public.keys.in.reply.and.keystore.don.t.match", "회신과 키 저장소의 공용 키가 일치하지 않습니다." }, { "Certificate.reply.and.certificate.in.keystore.are.identical", "회신과 키 저장소의 인증서가 동일합니다." }, { "Failed.to.establish.chain.from.reply", "회신의 체인 설정을 실패했습니다." }, { "n", "n" }, { "Wrong.answer.try.again", "잘못된 응답입니다. 다시 시도하십시오." }, { "Secret.key.not.generated.alias.alias.already.exists", "보안 키가 생성되지 않았으며 <{0}> 별칭이 존재합니다." }, { "Please.provide.keysize.for.secret.key.generation", "보안 키를 생성하려면 -keysize를 제공하십시오." }, { "warning.not.verified.make.sure.keystore.is.correct", "경고: 확인되지 않음. -keystore가 올바른지 확인하십시오." }, { "Extensions.", "확장: " }, { ".Empty.value.", "(비어 있는 값)" }, { "Extension.Request.", "확장 요청:" }, { "Unknown.keyUsage.type.", "알 수 없는 keyUsage 유형: " }, { "Unknown.extendedkeyUsage.type.", "알 수 없는 extendedkeyUsage 유형: " }, { "Unknown.AccessDescription.type.", "알 수 없는 AccessDescription 유형: " }, { "Unrecognized.GeneralName.type.", "알 수 없는 GeneralName 유형: " }, { "This.extension.cannot.be.marked.as.critical.", "이 확장은 중요한 것으로 표시할 수 없습니다. " }, { "Odd.number.of.hex.digits.found.", "홀수 개의 16진수가 발견됨: " }, { "Unknown.extension.type.", "알 수 없는 확장 유형: " }, { "command.{0}.is.ambiguous.", "{0} 명령이 모호함:" }, { "the.certificate.request", "인증서 요청" }, { "the.issuer", "발행자" }, { "the.generated.certificate", "생성된 인증서" }, { "the.generated.crl", "생성된 CRL" }, { "the.generated.certificate.request", "생성된 인증서 요청" }, { "the.certificate", "인증서" }, { "the.crl", "CRL" }, { "the.tsa.certificate", "TSA 인증서" }, { "the.input", "입력" }, { "reply", "회신" }, { "one.in.many", "%1$s #%2$d/%3$d" }, { "alias.in.cacerts", "cacerts의 <%s> 발행자" }, { "alias.in.keystore", "<%s> 발행자" }, { "with.weak", "%s(약함)" }, { "key.bit", "%1$d비트 %2$s 키" }, { "key.bit.weak", "%1$d비트 %2$s 키(약함)" }, { ".PATTERN.printX509Cert.with.weak", "소유자: {0}\n발행자: {1}\n일련 번호: {2}\n적합한 시작 날짜: {3} 종료 날짜: {4}\n인증서 지문:\n\t MD5:  {5}\n\t SHA1: {6}\n\t SHA256: {7}\n서명 알고리즘 이름: {8}\n주체 공용 키 알고리즘: {9}\n버전: {10}" }, { "PKCS.10.with.weak", "PKCS #10 인증서 요청(1.0 버전)\n제목: %1$s\n형식: %2$s\n공용 키: %3$s\n서명 알고리즘: %4$s\n" }, { "verified.by.s.in.s.weak", "%3$s을(를) 포함하는 %2$s의 %1$s에 의해 확인됨" }, { "whose.sigalg.risk", "%1$s이(가) 보안 위험으로 간주되는 %2$s 서명 알고리즘을 사용합니다." }, { "whose.key.risk", "%1$s이(가) 보안 위험으로 간주되는 %2$s을(를) 사용합니다." }, { "jks.storetype.warning", "%1$s 키 저장소는 고유 형식을 사용합니다. \"keytool -importkeystore -srckeystore %2$s -destkeystore %2$s -deststoretype pkcs12\"를 사용하는 산업 표준 형식인 PKCS12로 이전하는 것이 좋습니다." }, { "migrate.keystore.warning", "\"%1$s\"을(를) %4$s(으)로 이전했습니다. %2$s 키 저장소가 \"%3$s\"(으)로 백업되었습니다." }, { "backup.keystore.warning", "원본 키 저장소 \"%1$s\"이(가) \"%3$s\"(으)로 백업되었습니다." }, { "importing.keystore.status", "키 저장소 %1$s을(를) %2$s(으)로 임포트하는 중..." } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[][] getContents() {
/* 467 */     return contents;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/tools/keytool/Resources_ko.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */