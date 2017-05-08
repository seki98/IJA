all:
	wget http://sebastiankisela.com/sources.tgz
	tar -xvzf sources.tgz


zip:
	tar -czvf sources.tgz ./src/img/*
