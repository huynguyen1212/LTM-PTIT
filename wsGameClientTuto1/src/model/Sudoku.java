/**
 * Sudoku.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package model;

public class Sudoku  implements java.io.Serializable {
    private int[][] arrayValue;

    private int lastValue;

    private int lastX;

    private int lastY;

    public Sudoku() {
    }

    public Sudoku(
           int[][] arrayValue,
           int lastValue,
           int lastX,
           int lastY) {
           this.arrayValue = arrayValue;
           this.lastValue = lastValue;
           this.lastX = lastX;
           this.lastY = lastY;
    }


    /**
     * Gets the arrayValue value for this Sudoku.
     * 
     * @return arrayValue
     */
    public int[][] getArrayValue() {
        return arrayValue;
    }


    /**
     * Sets the arrayValue value for this Sudoku.
     * 
     * @param arrayValue
     */
    public void setArrayValue(int[][] arrayValue) {
        this.arrayValue = arrayValue;
    }


    /**
     * Gets the lastValue value for this Sudoku.
     * 
     * @return lastValue
     */
    public int getLastValue() {
        return lastValue;
    }


    /**
     * Sets the lastValue value for this Sudoku.
     * 
     * @param lastValue
     */
    public void setLastValue(int lastValue) {
        this.lastValue = lastValue;
    }


    /**
     * Gets the lastX value for this Sudoku.
     * 
     * @return lastX
     */
    public int getLastX() {
        return lastX;
    }


    /**
     * Sets the lastX value for this Sudoku.
     * 
     * @param lastX
     */
    public void setLastX(int lastX) {
        this.lastX = lastX;
    }


    /**
     * Gets the lastY value for this Sudoku.
     * 
     * @return lastY
     */
    public int getLastY() {
        return lastY;
    }


    /**
     * Sets the lastY value for this Sudoku.
     * 
     * @param lastY
     */
    public void setLastY(int lastY) {
        this.lastY = lastY;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Sudoku)) return false;
        Sudoku other = (Sudoku) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.arrayValue==null && other.getArrayValue()==null) || 
             (this.arrayValue!=null &&
              java.util.Arrays.equals(this.arrayValue, other.getArrayValue()))) &&
            this.lastValue == other.getLastValue() &&
            this.lastX == other.getLastX() &&
            this.lastY == other.getLastY();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getArrayValue() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getArrayValue());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getArrayValue(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += getLastValue();
        _hashCode += getLastX();
        _hashCode += getLastY();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Sudoku.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://model", "Sudoku"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("arrayValue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "arrayValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices", "ArrayOf_xsd_int"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://webservices", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastValue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "lastValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastX");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "lastX"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastY");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "lastY"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
