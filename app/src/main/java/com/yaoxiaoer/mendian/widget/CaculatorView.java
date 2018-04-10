package com.yaoxiaoer.mendian.widget;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.EditText;
import com.blankj.utilcode.util.SizeUtils;
import com.yaoxiaoer.mendian.R;
import com.yaoxiaoer.mendian.base.BaseRelativeLayout;

/**
 * Created by Chenwy on 2018/2/23.
 */

public class CaculatorView extends BaseRelativeLayout implements View.OnClickListener {

    private TextView btn0;
    private TextView btn00;
    /**
     * 点
     */
    private TextView btnDot;
    private TextView btn1;
    private TextView btn2;
    private TextView btn3;
    private TextView btn4;
    private TextView btn5;
    private TextView btn6;
    private TextView btn7;
    private TextView btn8;
    private TextView btn9;

    private TextView btnC;
    /**
     * 正负
     */
    private TextView btnDel;
    /**
     * 除
     */
    private TextView btnDivided;

    /**
     * 乘
     */
    private TextView btnX;

    /**
     * 减
     */
    private TextView btnSub;
    /**
     * 加
     */
    private TextView btnAdd;
    /**
     * 等于
     */
    private TextView btnEqual;

    /**
     * 确定
     */
    private TextView btnSure;

    private EditText etResult;

    /**
     * 已经输入的字符
     */
    private String existedText = "";
    /**
     * 是否计算过
     */
    private boolean isCounted = false;
    /**
     * 以负号开头，且运算符不是是减号
     * 例如：-21×2
     */
    private boolean startWithOperator = false;
    /**
     * 以负号开头，且运算符是减号
     * 例如：-21-2
     */
    private boolean startWithSubtract = false;
    /**
     * 不以负号开头，且包含运算符
     * 例如：21-2
     */
    private boolean noStartWithOperator = false;

    public CaculatorView(Context context) {
        super(context);
    }

    public CaculatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CaculatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.calculator;
    }

    @Override
    protected void init(AttributeSet attrs, int defStyleAttr) {
        setVisibility(GONE);

        initView();

        initListener();
    }

    private void initView() {
        btn0 = findViewById(R.id._0);
        btn00 = findViewById(R.id._00);
        btnDot = findViewById(R.id._dot);
        btn1 = findViewById(R.id._1);
        btn2 = findViewById(R.id._2);
        btn3 = findViewById(R.id._3);
        btn4 = findViewById(R.id._4);
        btn5 = findViewById(R.id._5);
        btn6 = findViewById(R.id._6);
        btn7 = findViewById(R.id._7);
        btn8 = findViewById(R.id._8);
        btn9 = findViewById(R.id._9);
        btnC = findViewById(R.id._C);
        btnDel = findViewById(R.id._del);
        btnDivided = findViewById(R.id._divided);
        btnX = findViewById(R.id._x);
        btnSub = findViewById(R.id._sub);
        btnAdd = findViewById(R.id._add);
        btnEqual = findViewById(R.id._equal);
        btnSure = findViewById(R.id.btn_sure);
        etResult = findViewById(R.id.et_result);

        existedText = etResult.getText().toString().trim();
    }

    private void initListener() {
        btn0.setOnClickListener(this);
        btn00.setOnClickListener(this);
        btnDot.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnDel.setOnClickListener(this);
        btnDivided.setOnClickListener(this);
        btnX.setOnClickListener(this);
        btnSub.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnEqual.setOnClickListener(this);
        btnSure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id._0:
                existedText = isOverRange(existedText, "0");
                break;
            case R.id._00:
                if (!existedText.equals("0")) {
                    if (!existedText.substring(existedText.length() - 1).equals("+")
                            && !existedText.substring(existedText.length() - 1).equals("-")
                            && !existedText.substring(existedText.length() - 1).equals("×")
                            && !existedText.substring(existedText.length() - 1).equals("÷")) {
                        existedText += "00";
                    }
                }
                break;
            //点
            case R.id._dot:
                if (!isCounted) {
                    if (existedText.contains("+") || existedText.contains("-") ||
                            existedText.contains("×") || existedText.contains("÷")) {
                        String param = null;
                        if (existedText.contains("+")) {
                            param = existedText.substring(existedText.indexOf("+") + 1);
                        } else if (existedText.contains("-")) {
                            param = existedText.substring(existedText.indexOf("-") + 1);
                        } else if (existedText.contains("×")) {
                            param = existedText.substring(existedText.indexOf("×") + 1);
                        } else if (existedText.contains("÷")) {
                            param = existedText.substring(existedText.indexOf("÷") + 1);
                        }
                        boolean isContainedDot = param.contains(".");
                        if (param.length() >= 9) {

                        } else if (!isContainedDot) {
                            if (param.equals("")) {
                                existedText += "0.";
                            } else {
                                existedText += ".";
                            }
                        } else {
                            return;
                        }
                    } else {
                        boolean isContainedDot = existedText.contains(".");
                        if (existedText.length() >= 9) {

                        } else if (!isContainedDot) {
                            existedText += ".";
                        } else {
                            return;
                        }
                    }
                    isCounted = false;

                } else {
                    existedText = "0.";
                    isCounted = false;
                }
                break;
            case R.id._1:
                existedText = isOverRange(existedText, "1");
                break;
            case R.id._2:
                existedText = isOverRange(existedText, "2");
                break;
            case R.id._3:
                existedText = isOverRange(existedText, "3");
                break;
            case R.id._4:
                existedText = isOverRange(existedText, "4");
                break;
            case R.id._5:
                existedText = isOverRange(existedText, "5");
                break;
            case R.id._6:
                existedText = isOverRange(existedText, "6");
                break;
            case R.id._7:
                existedText = isOverRange(existedText, "7");
                break;
            case R.id._8:
                existedText = isOverRange(existedText, "8");
                break;
            case R.id._9:
                existedText = isOverRange(existedText, "9");
                break;
            //归零
            case R.id._C:
                existedText = "0";
                break;
            //回删
            case R.id._del:
                if (existedText.equals("error")) {
                    existedText = "0";
                } else if (existedText.length() > 0) {
                    if (existedText.length() == 1) {
                        existedText = "0";
                    } else {
                        existedText = existedText.substring(0, existedText.length() - 1);
                    }
                }
                break;
            //除
            case R.id._divided:
                if (!existedText.contains("e")) {
                    if (judgeExpression()) {
                        existedText = getResult();
                        if (existedText.equals("error")) {

                        } else {
                            existedText += "÷";
                        }

                    } else {

                        if (isCounted) {
                            isCounted = false;
                        }

                        if ((existedText.substring(existedText.length() - 1)).equals("+")) {
                            existedText = existedText.replace("+", "÷");
                        } else if ((existedText.substring(existedText.length() - 1)).equals("-")) {
                            existedText = existedText.replace("-", "÷");
                        } else if ((existedText.substring(existedText.length() - 1)).equals("×")) {
                            existedText = existedText.replace("×", "÷");
                        } else if (!(existedText.substring(existedText.length() - 1)).equals("÷")) {
                            existedText += "÷";
                        }
                    }
                } else {
                    existedText = "0";
                }
                break;
            //乘
            case R.id._x:
                if (!existedText.contains("e")) {
                    if (judgeExpression()) {
                        existedText = getResult();
                        if (existedText.equals("error")) {

                        } else {
                            existedText += "×";
                        }

                    } else {

                        if (isCounted) {
                            isCounted = false;
                        }

                        if ((existedText.substring(existedText.length() - 1)).equals("+")) {
                            existedText = existedText.replace("+", "×");
                        } else if ((existedText.substring(existedText.length() - 1)).equals("-")) {
                            existedText = existedText.replace("-", "×");
                        } else if ((existedText.substring(existedText.length() - 1)).equals("÷")) {
                            existedText = existedText.replace("÷", "×");
                        } else if (!(existedText.substring(existedText.length() - 1)).equals("×")) {
                            existedText += "×";
                        }
                    }
                } else {
                    existedText = "0";
                }
                break;
            //减
            case R.id._sub:
                if (!existedText.contains("e")) {
                    if (judgeExpression()) {
                        existedText = getResult();
                        if (existedText.equals("error")) {

                        } else {
                            existedText += "-";
                        }
                    } else {

                        if (isCounted) {
                            isCounted = false;
                        }

                        if ((existedText.substring(existedText.length() - 1)).equals("+")) {
                            existedText = existedText.replace("+", "-");
                        } else if ((existedText.substring(existedText.length() - 1)).equals("×")) {
                            existedText = existedText.replace("×", "-");
                        } else if ((existedText.substring(existedText.length() - 1)).equals("÷")) {
                            existedText = existedText.replace("÷", "-");
                        } else if (!(existedText.substring(existedText.length() - 1)).equals("-")) {
                            existedText += "-";
                        }
                    }
                } else {
                    existedText = "0";
                }
                break;
            //加
            case R.id._add:
                /**
                 * 判断已有的字符是否是科学计数
                 * 是 置零
                 * 否 进行下一步
                 *
                 * 判断表达式是否可以进行计算
                 * 是 先计算再添加字符
                 * 否 添加字符
                 *
                 * 判断计算后的字符是否是 error
                 * 是 置零
                 * 否 添加运算符
                 */
                if (!existedText.contains("e")) {
                    if (judgeExpression()) {
                        existedText = getResult();
                        if (existedText.equals("error")) {

                        } else {
                            existedText += "+";
                        }
                    } else {

                        if (isCounted) {
                            isCounted = false;
                        }

                        if ((existedText.substring(existedText.length() - 1)).equals("-")) {
                            existedText = existedText.replace("-", "+");
                        } else if ((existedText.substring(existedText.length() - 1)).equals("×")) {
                            existedText = existedText.replace("×", "+");
                        } else if ((existedText.substring(existedText.length() - 1)).equals("÷")) {
                            existedText = existedText.replace("÷", "+");
                        } else if (!(existedText.substring(existedText.length() - 1)).equals("+")) {
                            existedText += "+";
                        }
                    }
                } else {
                    existedText = "0";
                }
                break;
            //等于
            case R.id._equal:
                existedText = getResult();
                if (existedText.substring(existedText.length() - 1).equals("+")
                        || existedText.substring(existedText.length() - 1).equals("-")
                        || existedText.substring(existedText.length() - 1).equals("×")
                        || existedText.substring(existedText.length() - 1).equals("÷")) {
                    if (existedText.length() > 0) {
                        existedText = existedText.substring(0, existedText.length() - 1);
                    }
                }
                isCounted = true;
                break;
            //确定
            case R.id.btn_sure:
                if (existedText.substring(existedText.length() - 1).equals("+")
                        || existedText.substring(existedText.length() - 1).equals("-")
                        || existedText.substring(existedText.length() - 1).equals("×")
                        || existedText.substring(existedText.length() - 1).equals("÷")) {
                    if (existedText.length() > 0) {
                        existedText = existedText.substring(0, existedText.length() - 1);
                    }
                } else {
                    if (existedText.contains("+") || existedText.contains("-") ||
                            existedText.contains("×") || existedText.contains("÷")) {
                        existedText = getResult();
                    }
                }
                if (onResultCallBack != null && !TextUtils.isEmpty(existedText)) {
                    if (TextUtils.isEmpty(existedText)) {
                        existedText = "0";
                    }
                    onResultCallBack.onResultCallBack(existedText);
                }
                isCounted = true;
                close();
                return;
        }
        etResult.setText(existedText);
    }

    public void open() {
        int height = SizeUtils.getMeasuredHeight(this);
        openAnim(this, height);
    }

    /**
     * 关闭
     */
    public void close() {
        existedText = "0";
        etResult.setText(existedText);
        closeAnimate(this);
    }

    private void openAnim(View v, int height) {
        v.setVisibility(View.VISIBLE);
        ValueAnimator animator = createDropAnimator(v, 0, height);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (onSwitchStatusChangeListener != null) {
                    onSwitchStatusChangeListener.open();
                }
            }
        });
        animator.start();
    }

    private void closeAnimate(final View view) {
        int origHeight = view.getHeight();
        ValueAnimator animator = createDropAnimator(view, origHeight, 0);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
                if (onSwitchStatusChangeListener != null) {
                    onSwitchStatusChangeListener.close();
                }
            }
        });
        animator.start();
    }

    private ValueAnimator createDropAnimator(final View v, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                int value = (int) arg0.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.height = value;
                v.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    /**
     * 进行运算，得到结果
     *
     * @return 返回结果
     */
    private String getResult() {

        /**
         * 结果
         */
        String tempResult = null;
        /**
         * 两个String类型的参数
         */
        String param1 = null;
        String param2 = null;
        /**
         * 转换后的两个double类型的参数
         */
        double arg1 = 0;
        double arg2 = 0;
        double result = 0;

        getCondition();

        /**
         * 如果有运算符，则进行运算
         * 没有运算符，则把已经存在的数据再传出去
         */
        if (startWithOperator || noStartWithOperator || startWithSubtract) {

            if (existedText.contains("+")) {
                /**
                 * 先获取两个参数
                 */
                param1 = existedText.substring(0, existedText.indexOf("+"));
                param2 = existedText.substring(existedText.indexOf("+") + 1);
                /**
                 * 如果第二个参数为空，则还是显示当前字符
                 */
                if (param2.equals("")) {
                    tempResult = existedText;
                } else {
                    /**
                     * 转换String为Double
                     * 计算后再转换成String类型
                     * 进行正则表达式处理
                     */
                    arg1 = Double.parseDouble(param1);
                    arg2 = Double.parseDouble(param2);
                    result = arg1 + arg2;
                    tempResult = String.format("%f", result);
                    tempResult = subZeroAndDot(tempResult);
                }


            } else if (existedText.contains("×")) {

                param1 = existedText.substring(0, existedText.indexOf("×"));
                param2 = existedText.substring(existedText.indexOf("×") + 1);

                if (param2.equals("")) {
                    tempResult = existedText;
                } else {
                    arg1 = Double.parseDouble(param1);
                    arg2 = Double.parseDouble(param2);
                    result = arg1 * arg2;
                    tempResult = String.format("%f", result);
                    tempResult = subZeroAndDot(tempResult);
                }

            } else if (existedText.contains("÷")) {

                param1 = existedText.substring(0, existedText.indexOf("÷"));
                param2 = existedText.substring(existedText.indexOf("÷") + 1);

                if (param2.equals("0")) {
                    tempResult = "error";
                } else if (param2.equals("")) {
                    tempResult = existedText;
                } else {
                    arg1 = Double.parseDouble(param1);
                    arg2 = Double.parseDouble(param2);
                    result = arg1 / arg2;
                    tempResult = String.format("%f", result);
                    tempResult = subZeroAndDot(tempResult);
                }

            } else if (existedText.contains("-")) {

                /**
                 * 这里是以最后一个 - 号为分隔去取出两个参数
                 * 进到这个方法，必须满足有运算公式
                 * 而又避免了第一个参数是负数的情况
                 */
                param1 = existedText.substring(0, existedText.lastIndexOf("-"));
                param2 = existedText.substring(existedText.lastIndexOf("-") + 1);

                if (param2.equals("")) {
                    tempResult = existedText;
                } else {
                    arg1 = Double.parseDouble(param1);
                    arg2 = Double.parseDouble(param2);
                    result = arg1 - arg2;
                    tempResult = String.format("%f", result);
                    tempResult = subZeroAndDot(tempResult);
                }

            }
            /**
             * 如果数据长度大于等于10位，进行科学计数
             *
             * 如果有小数点，再判断小数点前是否有十个数字，有则进行科学计数
             */
            if (tempResult.length() >= 10) {
                tempResult = String.format("%e", Double.parseDouble(tempResult));
            } else if (tempResult.contains(".")) {
                if (tempResult.substring(0, tempResult.indexOf(".")).length() >= 10) {
                    tempResult = String.format("%e", Double.parseDouble(tempResult));
                }
            }
        } else {
            tempResult = existedText;
        }

        return tempResult;
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     *
     * @param s 传入的字符串
     * @return 修改之后的字符串
     */
    private String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    /**
     * 判断表达式
     * <p>
     * 为了按等号是否进行运算
     * 以及出现两个运算符（第一个参数如果为负数的符号不计）先进行运算再添加运算符
     */
    private boolean judgeExpression() {

        getCondition();

        String tempParam2 = null;

        if (startWithOperator || noStartWithOperator || startWithSubtract) {

            if (existedText.contains("+")) {
                /**
                 * 先获取第二个参数
                 */
                tempParam2 = existedText.substring(existedText.indexOf("+") + 1);
                /**
                 * 如果第二个参数为空，表达式不成立
                 */
                if (tempParam2.equals("")) {
                    return false;
                } else {
                    return true;
                }
            } else if (existedText.contains("×")) {

                tempParam2 = existedText.substring(existedText.indexOf("×") + 1);

                if (tempParam2.equals("")) {
                    return false;
                } else {
                    return true;
                }

            } else if (existedText.contains("÷")) {

                tempParam2 = existedText.substring(existedText.indexOf("÷") + 1);

                if (tempParam2.equals("")) {
                    return false;
                } else {
                    return true;
                }

            } else if (existedText.contains("-")) {

                /**
                 * 这里是以最后一个 - 号为分隔去取出两个参数
                 * 进到这个方法，必须满足有运算公式
                 * 而又避免了第一个参数是负数的情况
                 */
                tempParam2 = existedText.substring(existedText.lastIndexOf("-") + 1);

                if (tempParam2.equals("")) {
                    return false;
                } else {
                    return true;
                }

            }
        }
        return false;
    }

    /**
     * 取得判断条件
     */
    private void getCondition() {
        /**
         * 以负号开头，且运算符不是是减号
         * 例如：-21×2
         */
        startWithOperator = existedText.startsWith("-") && (existedText.contains("+") ||
                existedText.contains("×") || existedText.contains("÷"));
        /**
         * 以负号开头，且运算符是减号
         * 例如：-21-2
         */
        startWithSubtract = existedText.startsWith("-") && (existedText.lastIndexOf("-") != 0);
        /**
         * 不以负号开头，且包含运算符
         * 例如：21-2
         */
        noStartWithOperator = !existedText.startsWith("-") && (existedText.contains("+") ||
                existedText.contains("-") || existedText.contains("×") || existedText.contains("÷"));
    }


    /**
     * 先判断是否按过等于号
     * 是 按数字则显示当前数字
     * 否 在已有的表达式后添加字符
     * <p>
     * 判断数字是否就是一个 0
     * 是 把字符串设置为空字符串。
     * 1、打开界面没有运算过的时候，AC键归零或删除完归零的时候，会显示一个 0
     * 2、当数字是 0 的时候，设置成空字符串，再按 0 ，数字会还是 0，不然可以按出 000 这种数字
     * 否 添加按下的键的字符
     * <p>
     * 判断数字是否包含小数点
     * 是 数字不能超过十位
     * 否 数字不能超过九位
     * <p>
     * 进行上面的判断后，再判断数字是否超过长度限制
     * 超过不做任何操作
     * 没超过可以再添数字
     */
    private String isOverRange(String existedText, String s) {
        /**
         * 判断是否计算过
         */
        if (!isCounted) {
            /**
             * 判断是否是科学计数
             * 是 文本置零
             */
            if (existedText.contains("e")) {
                existedText = "0";
            }
            /**
             * 判断是否只有一个 0
             * 是 文本清空
             */
            if (existedText.equals("0")) {
                existedText = "";
            }
            /**
             * 判断是否有运算符
             * 是 判断第二个数字
             * 否 判断整个字符串
             */
            if (existedText.contains("+") || existedText.contains("-") ||
                    existedText.contains("×") || existedText.contains("÷")) {
                /**
                 * 包括运算符时 两个数字 判断第二个数字
                 * 两个参数
                 */
                String param2 = null;
                if (existedText.contains("+")) {
                    param2 = existedText.substring(existedText.indexOf("+") + 1);
                } else if (existedText.contains("-")) {
                    param2 = existedText.substring(existedText.indexOf("-") + 1);
                } else if (existedText.contains("×")) {
                    param2 = existedText.substring(existedText.indexOf("×") + 1);
                } else if (existedText.contains("÷")) {
                    param2 = existedText.substring(existedText.indexOf("÷") + 1);
                }
                if (existedText.substring(existedText.length() - 1).equals("+") ||
                        existedText.substring(existedText.length() - 1).equals("-") ||
                        existedText.substring(existedText.length() - 1).equals("×") ||
                        existedText.substring(existedText.length() - 1).equals("÷")) {
                    existedText += s;
                } else {
                    if (param2.contains(".")) {
                        if (param2.length() >= 10) {

                        } else {
                            existedText += s;
                        }
                    } else {
                        if (param2.length() >= 9) {

                        } else {
                            existedText += s;
                        }
                    }
                }
            } else {
                /**
                 * 不包括运算符时 一个数字
                 */
                if (existedText.contains(".")) {
                    if (existedText.length() >= 10) {

                    } else {
                        existedText += s;
                    }
                } else {
                    if (existedText.length() >= 9) {

                    } else {
                        existedText += s;
                    }
                }
            }

            isCounted = false;

        } else {

            existedText = s;
            isCounted = false;

        }


        return existedText;
    }

    private OnResultCallBack onResultCallBack;
    private OnSwitchStatusChangeListener onSwitchStatusChangeListener;

    public void setOnVisiableStatusChangeListener(OnSwitchStatusChangeListener onSwitchStatusChangeListener) {
        this.onSwitchStatusChangeListener = onSwitchStatusChangeListener;
    }

    public CaculatorView setOnResultCallBack(OnResultCallBack onResultCallBack) {
        this.onResultCallBack = onResultCallBack;
        return this;
    }

    public interface OnResultCallBack {
        void onResultCallBack(String result);
    }

    public interface OnSwitchStatusChangeListener {
        void open();

        void close();
    }
}
