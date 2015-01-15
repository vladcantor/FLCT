package LexicalScanner;


import java.io.IOException;

import ScanningResult.ScanningResult;
import ScanningTools.CodificationTable;
import ScanningTools.Constants;
import ScanningTools.FileIterator;
import ScanningTools.InvalidTokenException;
import ScanningTools.LexicalItemsManager;

public class LexicalScanner {
	
	public static ScanningResult Scan(String filepath) throws IOException, InvalidTokenException {
		ScanningResult result = new ScanningResult();
		String token;

		FileIterator fiterator = new FileIterator(filepath);
		LexicalItemsManager manager = LexicalItemsManager.getInstance();
		CodificationTable codingTable = CodificationTable.getInstance();

		while (fiterator.hasNextToken()) {
			token = fiterator.nextToken();
			if (manager.isOperator(token) || manager.isSeparator(token) || manager.isReservedWord(token))
				result.addEntryToPIF(codingTable.getCode(token));
			else if (manager.isConstant(token)) {
				int index = result.getPositionInConstantsST(token);
				result.addEntryToPIF(Constants.CONSTANT_CODE, index);
			}
			else if (manager.isIdentifier(token)) {
				int index = result.getPositionInIdentifiersST(token);
				result.addEntryToPIF(Constants.IDENTIFIER_CODE, index);
			}
			else {
				throw new InvalidTokenException(token, fiterator.getLine());
			}
		}
		result.savePIF(Constants.PIF_RESULT_FILE);
		result.saveConstantsST(Constants.CONST_ST_RESULT_FILE);
		result.saveIdentifiersST(Constants.ID_ST_RESULT_FILE);
		
		return result;
	}
	

}
