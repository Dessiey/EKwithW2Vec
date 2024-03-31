import matplotlib.pyplot
import gensim
import os,sys,nltk,string,glob,numpy
import pandas as pd,re
from gensim.models import Word2Vec
from gensim.models import word2vec
from sklearn.decomposition import PCA
from matplotlib import pyplot
from glob import iglob
from itertools import chain
from nltk.corpus import stopwords
import datetime
import l3
from gensim.models.keyedvectors import KeyedVectors
exclude = set(string.punctuation)

#print("{} {}".format(str(datetime.datetime.now()), 'running at '))
#print("anal_word('am','????')")
#print(l3.anal('am', '????'))#generation of root words

original_document = open("F:/AAU/research/data/ethio reporter/art and kinetbeb/art (1).txt",encoding='utf-8')
list_of_stop_words=open("F:/AAU/research/data/list_of_stop_words.txt",encoding='utf-8')
filterd=open("D:/nwart_filtered.txt",mode="w",encoding='utf-8')
contents = original_document.readlines()
sw=list_of_stop_words.readlines()
original_document.close()
list_of_stop_words.close()

nw_contents=[]
for x in sw:
    if not x.strip():
        continue
    else:
        nw_contents.append(x) 
nw_contents=''.join(nw_contents)
#nw_contents = ''.join(ch for ch in nw_contents if ch not in exclude) 
nw_contents=re.sub(r"[?,?]","?",nw_contents)
nw_contents= re.sub(r"[?,?]" , "?",nw_contents)
nw_contents= re.sub(r"[?,?]", "?",nw_contents)
nw_contents= re.sub(r"[?,?]", "?",nw_contents)
nw_contents= re.sub(r"[?,?]", "?",nw_contents)
nw_contents= re.sub(r"[?,?]", "?",nw_contents)
nw_contents= re.sub(r"[?,?]", "?",nw_contents)

nw_contents=re.sub(r"[?]","?",nw_contents)
nw_contents=re.sub(r"[?]","?",nw_contents)
nw_contents=re.sub(r"[?]","?",nw_contents) #normalizing ?  to ?
nw_contents=re.sub(r"[?]","?",nw_contents)
nw_contents=re.sub(r"[?]","?",nw_contents)
nw_contents=re.sub(r"[?]","?",nw_contents)
nw_contents=re.sub(r"[?]","?",nw_contents)

nw_contents=re.sub(r"[?]","?",nw_contents)
nw_contents=re.sub(r"[?]","?",nw_contents)
nw_contents=re.sub(r"[?]","?",nw_contents) #normalizing ?  to ?
nw_contents=re.sub(r"[?]","?",nw_contents)
nw_contents=re.sub(r"[?]","?",nw_contents)
nw_contents=re.sub(r"[?]","?",nw_contents)
nw_contents=re.sub(r"[?]","?",nw_contents)

nw_contents=re.sub(r"[?]","?",nw_contents)
nw_contents=re.sub(r"[?]","?",nw_contents)
nw_contents=re.sub(r"[?]","?",nw_contents) #normalizing ?  to ?
nw_contents=re.sub(r"[?]","?",nw_contents)
nw_contents=re.sub(r"[?]","?",nw_contents)
nw_contents=re.sub(r"[?]","?",nw_contents)
nw_contents=re.sub(r"[?]","?",nw_contents)

nw_contents=nw_contents.replace('?','') 
nw_contents=nw_contents.replace('? ','')
nw_contents=nw_contents.replace('::','')
nw_contents=nw_contents.replace('? ','')
nw_contents=nw_contents.replace('?  ','')
nw_contents=nw_contents.replace(':','?')
nw_contents=nw_contents.replace('?','')
nw_contents=nw_contents.replace('??','')
nw_contents=nw_contents.replace('?','')
nw_contents=nw_contents.replace('?','')
nw_contents=nw_contents.replace('  ','')
nw_contents=nw_contents.replace(' ','')
nw_contents=nw_contents.replace('?','')
nw_contents=nw_contents.replace('?','')
nw_contents=nw_contents.replace('?','')
nw_contents=nw_contents.replace('?','')
nw_contents=nw_contents.replace('(','')
nw_contents=nw_contents.replace(')','')
nw_contents=nw_contents.replace('.','')
nw_contents=nw_contents.replace('-','')
nw_contents=nw_contents.replace('  ','')
nw_contents=nw_contents.replace('=','')
nw_contents = re.sub(r"[,@\'?\.$%_/]", "", nw_contents)
nw_contents=re.sub("[a-zA-Z]+","",nw_contents) #removing English characters
nw_contents=re.sub('[0-9]+', '', nw_contents)  #removing Numbers 
nw_contents=re.split(r' *[\?\?!][\'"\)\]]* *', nw_contents)
#oo=[x.split() for x in nw_contents]
#print("List of Stop Words \n",nw_contents)
new_contents = []
# Get rid of empty lines
for line in contents:
    # Strip whitespace, should leave nothing if empty line was just "\n"
    if not line.strip():
        continue
    # We got something, save it
    else:
        new_contents.append(line)

new_contents=''.join(new_contents) 
#new_contents = ''.join(ch for ch in new_contents if ch not in exclude)
#n = filter(lambda x: True if x==' ' else x not in string.printable , new_contents)
#n = filter(lambda x: x in string.whitespace or x not in string.printable, n)
new_contents=re.sub(r"[?,?]","?",new_contents)
new_contents= re.sub(r"[?,?]" , "?",new_contents) #normalizing ? and ?  to ?
new_contents= re.sub(r"[?,?]", "?",new_contents)
new_contents= re.sub(r"[?,?]", "?",new_contents)
new_contents= re.sub(r"[?,?]", "?",new_contents)
new_contents= re.sub(r"[?,?]", "?",new_contents)
new_contents= re.sub(r"[?,?]", "?",new_contents)

new_contents=re.sub(r"[?]","?",new_contents)
new_contents=re.sub(r"[?]","?",new_contents)
new_contents=re.sub(r"[?]","?",new_contents) #normalizing ?  to ?
new_contents=re.sub(r"[?]","?",new_contents)
new_contents=re.sub(r"[?]","?",new_contents)
new_contents=re.sub(r"[?]","?",new_contents)
new_contents=re.sub(r"[?]","?",new_contents)


new_contents=re.sub(r"[?]","?",new_contents)
new_contents=re.sub(r"[?]","?",new_contents)
new_contents=re.sub(r"[?]","?",new_contents) #normalizing ?  to ?
new_contents=re.sub(r"[?]","?",new_contents)
new_contents=re.sub(r"[?]","?",new_contents)
new_contents=re.sub(r"[?]","?",new_contents)
new_contents=re.sub(r"[?]","?",new_contents)

new_contents=re.sub(r"[?]","?",new_contents)
new_contents=re.sub(r"[?]","?",new_contents)
new_contents=re.sub(r"[?]","?",new_contents) #normalizing ?  to ?
new_contents=re.sub(r"[?]","?",new_contents)
new_contents=re.sub(r"[?]","?",new_contents)
new_contents=re.sub(r"[?]","?",new_contents)
new_contents=re.sub(r"[?]","?",new_contents)

new_contents=re.sub('?/?','????',new_contents)  
new_contents=re.sub('?/?','??? ????',new_contents)
new_contents=re.sub('?/?','??? ???',new_contents)
new_contents=re.sub('?/?/?','?????? ?????? ????',new_contents)
new_contents=re.sub('?/?/?','??? ?????? ?????',new_contents)
new_contents=re.sub('?/?/?','?????? ?? ??????',new_contents)
new_contents=re.sub('??/?/?','?? ???????? ?????',new_contents)
new_contents=re.sub('?/?','?????',new_contents)
new_contents=re.sub('?/???','??? ???',new_contents)
new_contents=re.sub('?/?','??? ???',new_contents)
new_contents=re.sub('?/??','???? ??',new_contents)
new_contents=re.sub('?/?','?????',new_contents)
new_contents=re.sub('?/?/?','???? ?????',new_contents)
new_contents=re.sub('?/?/?/?','???? ???? ?????',new_contents)
new_contents=re.sub('?/?','?????',new_contents)
new_contents=re.sub('?/?/?/?','?????? ?????? ???? ??-??????',new_contents)
new_contents=re.sub('?/??','????? ??',new_contents)
new_contents=re.sub('?/?','??? ????',new_contents)
new_contents=re.sub('?/?/?','???? ???? ????',new_contents)
new_contents=re.sub('?/?/?','???? ?? ????',new_contents)
new_contents=re.sub('?/??/??/?','????? ????? ??? ????',new_contents)
new_contents=re.sub('?/??/??/?','????? ????? ??? ????',new_contents)
new_contents=re.sub('?/??','??? ??',new_contents)
new_contents=re.sub('?/?','????',new_contents)
new_contents=re.sub('?/??','??? ??',new_contents)
new_contents=re.sub('?/?','????',new_contents)
new_contents=re.sub('?/?/??','????? ???? ??',new_contents)
new_contents=re.sub('?/?','????',new_contents)
new_contents=re.sub('???','??? ????? ????',new_contents)
new_contents=re.sub('???','?????',new_contents)
new_contents=re.sub('????','???? ???',new_contents)
                 
new_contents=new_contents.replace('?','') 
new_contents=new_contents.replace('? ','')
new_contents=new_contents.replace('::','')
new_contents=new_contents.replace('? ','')
new_contents=new_contents.replace('?  ','')
new_contents=new_contents.replace(':','?')
new_contents=new_contents.replace('?','')
new_contents=new_contents.replace('??','')
new_contents=new_contents.replace('?','')
new_contents=new_contents.replace('?','')
new_contents=new_contents.replace('  ','')
new_contents=new_contents.replace(' ','')
new_contents=new_contents.replace('?','')
new_contents=new_contents.replace('?','')
new_contents=new_contents.replace('?','')
new_contents=new_contents.replace('?','')
new_contents=new_contents.replace('(','')
new_contents=new_contents.replace(')','')
new_contents=new_contents.replace('.','')
new_contents=new_contents.replace('-','')
new_contents=new_contents.replace(' ','')
new_contents=new_contents.replace('=','')
new_contents = re.sub(r"[,@\'?\.$%_/]", "", new_contents)
#new_contents = re.sub("http://[a-zA-z./\d]*","",new_contents) removing links
new_contents=re.sub("[a-zA-Z]+","",new_contents) #remove english words
new_contents=re.sub('[0-9]+', '', new_contents)  #removing numbers 
new_contents = re.split(r' *[\?\?!][\'"\)\]]* *', new_contents)

#content = [s.split() for s in new_contents]
word_list = []
for i in new_contents:  #remove html tags 
       if (('http' not in i) and ('@' not in i) and ('<.*?>' not in i)):
            word_list += [i]
#filtrate = re.compile(u'[^U+1200-U+137F]') # non-Amharic unicode range
#new_contents = filtrate.sub(r'', new_contents) # remove all non-Amharic characters
#new_contents = new_contents.encode("utf-8") # convert unicode back to str    

#context = re.sub("http://[a-zA-z./\d]*","",context) #remove links
#new_contentss=[]
#new_contents=[w for w in new_contents if not re.match('[A-Z]+', w, re.I)]
#for word in new_contents:
#    if not word.isalpha():
#         print(word)
#   else:
#       continue
#cnt = [s.split() for s in cnt]
#print("original document",new_contents)
#filtered_sentence = [w for w in new_contents if w not in nw_contents]
filtered_document = []
stoplist=[]
for line in nw_contents:
    w = line.split()
    for word in w:
        stoplist.append(word)
#end 
for line in word_list:
    w = line.split()
    for word in w:
        if word in stoplist: continue
        else: 
            filtered_document.append(word)
            filterd.write(word+' ')
            #word=l3.anal_word('am',word)
            #filterd.write(l3.anal_word('am',word)+" ") 
#filterd.write(filtered_document)            
filterd.close()
#cnt = [s.split() for s in filtered_document]
#for x in filtered_document: 
#    l3.anal_word('am',x)
#print("filtered doc is  \n",filtered_document)
#morphological analyzer
"""words=[]
sour_file='F:/class AAU/research dsm/data/filterd_document_art_and_kinetbeb.txt'
anal_file='F:/class AAU/research dsm/data/wer.txt'
l3.anal_file('am',sour_file ,anal_file)
with open(anal_file,encoding="utf8") as f:
 for line in f:
   parts = line.rstrip('\n').split(" ")
   i=0
   for x in parts:
     if x=='stem:':  
       words.append(parts[i+1])
       filterd.write(parts[i+1])
     i=i+1  
print("words",words)"""

#print('filtered_document\n',filtered_document)
print("\nthe filtered and purified document is writen to the disk")
#LEARNING ALGORITHM HERE
"""model = gensim.models.Word2Vec(cnt,size=300,alpha=0.025,window=5,min_count=10,workers=5,min_alpha=0.0001,sg=0,cbow_mean=1,sorted_vocab=1)
# summarize the loaded model
#print('model is ssss \n',model)
# summarize vocabulary
#class gensim.models.word2vec.Word2Vec(sentences=None, size=100, alpha=0.025, window=5, min_count=5, max_vocab_size=None, sample=0, seed=1, workers=1, min_alpha=0.0001, sg=1, hs=1, negative=0, cbow_mean=0, hashfxn=, iter=1, null_word=0, trim_rule=None, sorted_vocab=1)

#print('List of learned words are\n\n',words)
# access vector for one word
#print(model['sentence'])
# save model
model.wv.save_word2vec_format('w2v_bible.txt')
model.wv.save_word2vec_format('w2v_bible.model')
model.wv.save_word2vec_format('w2v_bible.vec')
model.wv.save_word2vec_format('w2v_bible.bin')
#model.save('w2v_python.txt')
# load model
my_test =KeyedVectors.load_word2vec_format('C:/Users/Pc/w2v_pythonnn.vec',encoding='utf-8',unicode_errors='ignore',limit=1000)  
words = list(my_test.wv.vocab)
X = my_test[my_test.wv.vocab]
pca = PCA(n_components=2)
result = pca.fit_transform(X)
# create a scatter plot of the projection
pyplot.scatter(result[:, 0], result[:, 1])
words = list(my_test.wv.vocab)
for i, word in enumerate(words):
	pyplot.annotate(word, xy=(result[i, 0], result[i, 1]))
pyplot.show()

#word2vec.Word2Vec(c, size=100, window=5, min_count=1, workers=4)"""

