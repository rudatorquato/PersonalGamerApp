package util;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class Mask {
    private static final String PhoneMask = "(##) #####-####";

    public static String unmask(String s) {
        return s.replaceAll("[^0-9]*", "");
    }

    public static String setMask(String str) {
        String maskAux = "";
        String value = str;
        String mask = PhoneMask;

        int i = 0;
        for (char m : mask.toCharArray()) {
            if ((m != '#')) {
                maskAux += m;
                continue;
            }

            try {
                maskAux += value.charAt(i);
            } catch (Exception e) {
                break;
            }
            i++;
        }
        return maskAux;
    }

    public static TextWatcher insert(final EditText editText, String maskType) {
        return new TextWatcher() {

            boolean isUpdating;
            String oldValue = "";

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String value = Mask.unmask(s.toString());
                String mask = PhoneMask;

//                switch (maskType) {
//                    case CPF:
//                        mask = CPFMask;
//                        break;
//                    case CNPJ:
//                        mask = CNPJMask;
//                        break;
//                    case DATA:
//                        mask = DATAMask;
//                        break;
//                    case HORA:
//                        mask = HORAMask;
//                        break;
//                    default:
//                        mask = getDefaultMask(value);
//                        break;
//                }

                String maskAux = "";
                if (isUpdating) {
                    oldValue = value;
                    isUpdating = false;
                    return;
                }
                int i = 0;
                for (char m : mask.toCharArray()) {
                    if ((m != '#' && value.length() > oldValue.length()) || (m != '#' && value.length() < oldValue.length() && value.length() != i)) {
                        maskAux += m;
                        continue;
                    }

                    try {
                        maskAux += value.charAt(i);
                    } catch (Exception e) {
                        break;
                    }
                    i++;
                }
                isUpdating = true;
                editText.setText(maskAux);
                editText.setSelection(maskAux.length());
            }

            public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
            public void afterTextChanged(Editable s) {}
        };
    }
}