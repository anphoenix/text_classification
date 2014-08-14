package com.ibm.classification;

public class Config {

	public static final String SOURCE_DATA_PATH = "data/data.txt";
	public static final String FEATURE_VECTOR = "data/words.txt";
	public static final int TRAIN_NUM = 700;
	
	public static String DOCPATH = "data/originaldocs/";
    public static String CH_STOPWORDS_FILE = "data/ch_stopwords/ch_stopword.txt";

    //Configuration for LDA
	public static String LDA_RESULT = "data/output/lda/";
    public static String LDA_PARAMETERFILE = "data/lda_parameter/parameters.txt";

    //Configuration for PLSA
    public static String PLSA_RESULT = "data/output/plsa/";
    public static String PLSA_PARAMETERFILE = "data/plsa_parameter/parameters.txt";

    public static String CH_POS_FILTER = "data/tlda_parameter/filter.txt";
    public static String CH_MODEL_SEG = "dependencies/models/seg.m";
    public static String CH_MODEL_POS = "dependencies/models/pos.m";
    public static String CH_MODEL_DICT = "dependencies/models/dict.txt";

    
    public static final String SOURCE_SEED = "seed";
	public static final String SOURCE_CHARSET = "charset";
	public static final String SOURCE_SITE = "site";
	public static final String SOURCE_INFO_CATEGORY = "category";
	public static final String SOURCE_INFO_LIST_PATTERN = "list_pattern";
	public static final String SOURCE_INFO_NEXT_PATTERN = "next_pattern";
	public static final String SOURCE_INFO_NEXT_TYPE = "next_type";
	public static final String SOURCE_DATE_FORMAT = "date_format";
	public static final String SOURCE_SAVE_NAME = "save_name";

	
	public static final String SOURCE_INFO_TITLE = "title";
	public static final String SOURCE_INFO_DATE = "date";
	public static final String SOURCE_AUTHOR = "author";
	public static final String SOURCE_AUTHOR_NAME = "name";
	public static final String SOURCE_AUTHOR_PNUM = "pnum";
	public static final String SOURCE_AUTHOR_SNUM = "snum";
	public static final String SOURCE_INFO_DESCRIBE = "describe";
	public static final String SOURCE_INFO_REPLY_TITLE = "reply_title";
	public static final String SOURCE_INFO_REPLY_CONTENT = "reply_content";
	public static final String SOURCE_INFO_SUPPORT = "support";
	public static final String SOURCE_INFO_OPPOSE = "oppose";
	public static final String SOURCE_INFO_SCAN = "scan";
	public static final String SOURCE_URL = "source_url";
    
    


}
