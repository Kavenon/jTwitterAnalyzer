/*-
 *
 *  * Copyright 2015 Skymind,Inc.
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 */

package pl.edu.agh.student.style;

import org.deeplearning4j.text.tokenization.tokenizer.NGramTokenizer;
import org.deeplearning4j.text.tokenization.tokenizer.TokenPreProcess;
import org.deeplearning4j.text.tokenization.tokenizer.Tokenizer;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;

import java.io.InputStream;

public class OwnNGramTokenizerFactory implements TokenizerFactory {
    private TokenPreProcess preProcess;
    private Integer minN = 1;
    private Integer maxN = 1;
    private TokenizerFactory tokenizerFactory;

    public OwnNGramTokenizerFactory(TokenizerFactory tokenizerFactory, Integer minN, Integer maxN) {
        this.tokenizerFactory = tokenizerFactory;
        this.minN = minN;
        this.maxN = maxN;
    }

    @Override
    public Tokenizer create(String toTokenize) {
        Tokenizer t1 = tokenizerFactory.create(toTokenize);
        t1.setTokenPreProcessor(preProcess);
        return new NGramTokenizer(t1, minN, maxN);
    }

    @Override
    public Tokenizer create(InputStream toTokenize) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setTokenPreProcessor(TokenPreProcess preProcessor) {
        this.preProcess = preProcessor;
    }

    @Override
    public TokenPreProcess getTokenPreProcessor() {
        return preProcess;
    }
}
