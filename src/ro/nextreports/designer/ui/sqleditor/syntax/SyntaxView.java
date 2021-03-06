/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ro.nextreports.designer.ui.sqleditor.syntax;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Shape;
import java.util.Iterator;

import javax.swing.event.DocumentEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.PlainView;
import javax.swing.text.Segment;
import javax.swing.text.Utilities;
import javax.swing.text.ViewFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Decebal Suiu
 */
public class SyntaxView extends PlainView {

    private static final Log LOG = LogFactory.getLog(SyntaxView.class.getName());

    public SyntaxView(Element element) {
        super(element);
    }

    @Override
    protected int drawUnselectedText(Graphics graphics, int x, int y, int p0, int p1) {
        Font saveFont = graphics.getFont();
        Color saveColor = graphics.getColor();
        SyntaxDocument doc = (SyntaxDocument) getDocument();
        Segment segment = getLineBuffer();
        try {
            // Colour the parts
            Iterator<Token> tokens = doc.getTokens(p0, p1);
            int start = p0;
            while (tokens.hasNext()) {
                Token t = tokens.next();
                // if there is a gap between the next token start and where we
                // should be starting (spaces not returned in tokens), then draw
                // it in the default type
                if (start < t.start) {
                    SyntaxStyles.getInstance().setGraphicsStyle(graphics,
                            TokenType.DEFAULT);
                    doc.getText(start, t.start - start, segment);
                    x = Utilities.drawTabbedText(segment, x, y, graphics, this, start);
                }
                // t and s are the actual start and length of what we should
                // put on the screen.  assume these are the whole token....
                int l = t.length;
                int s = t.start;
                // ... unless the token starts before p0:
                if (s < p0) {
                    // token is before what is requested. adgust the length and s
                    l -= (p0 - s);
                    s = p0;
                }
                // if token end (s + l is still the token end pos) is greater 
                // than p1, then just put up to p1
                if (s + l > p1) {
                    l = p1 - s;
                }
                doc.getText(s, l, segment);
                x = SyntaxStyles.getInstance().drawText(segment, x, y, graphics, this, t);
                start = t.start + t.length;
            }
            // now for any remaining text not tokenized:
            if (start < p1) {
                SyntaxStyles.getInstance().setGraphicsStyle(graphics, TokenType.DEFAULT);
                doc.getText(start, p1 - start, segment);
                x = Utilities.drawTabbedText(segment, x, y, graphics, this, start);
            }
        } catch (BadLocationException e) {
            System.err.println("Requested: " + e.offsetRequested());
            LOG.error(e.getMessage(), e);
        } finally {
            graphics.setFont(saveFont);
            graphics.setColor(saveColor);
        }
        
        return x;
    }

    @Override
    protected void updateDamage(DocumentEvent changes, Shape a, ViewFactory f) {
        super.updateDamage(changes, a, f);
        getContainer().repaint();
    }
    
}
