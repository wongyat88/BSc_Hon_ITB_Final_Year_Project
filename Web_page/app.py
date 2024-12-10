
import cx_Oracle
import random, string, datetime

from flask import Flask, render_template, request, redirect, url_for, session, abort, flash
from flask_mail import Mail, Message
from flask_babel import Babel, format_date, gettext
from datetime import date

app = Flask(__name__)
app.secret_key = 'FinalProject2020'

#Flask-Mail setting for sending Email
app.config['MAIL_SERVER'] = "smtp.outlook.com"
app.config['MAIL_PORT'] = 587
app.config['MAIL_USE_SSL'] = False
app.config['MAIL_USE_TLS'] = True,
app.config['MAIL_USERNAME'] = 'ilovepython2018@outlook.com'
app.config['MAIL_PASSWORD'] = 'python123'
app.config['MAIL_DEFAULT_SENDER'] = 'ilovepython2018@outlook.com'
app.config['FLASKY_ADMIN']  = 'ilovepython2018@outlook.com'
mail = Mail(app)

#Flask Babel for translation
app.config['BABEL_DEFAULT_LOCALE'] = 'en'
babel = Babel(app)

#Database connection
conn = cx_Oracle.connect('stu044/cestd1001@144.214.177.102/xe', encoding = "UTF-8", nencoding = "UTF-8")

#Variable used in this program
language = 'eng'
code = ''

@babel.localeselector
def get_locale():
    if language == 'eng':
        session['language'] = 'eng'
        return 'en'
    elif language == 'zh':
        session['language'] = 'zh'
        return 'zh'
    else:
        session['language'] = 'eng'
        return 'en'

def add_dict( first_array , second_array ):
    if isinstance( first_array , list ) and isinstance( second_array , list ):
            return first_array + second_array
    elif isinstance( first_array , dict ) and isinstance( second_array , dict ):
            return dict( list( first_array.items() ) + list( second_array.items() ) )
    elif isinstance( first_array , set ) and isinstance( second_array , set ):
            return first_array.union( second_array )
    return False

@app.route('/lang')
def changeLang():
    path = session['path']
    global language
    if language == 'eng':
        language = 'zh'
    elif language == 'zh':
        language = 'eng'
    return redirect(url_for(path))

@app.route("/")
def index():
    session['path'] = 'index'
    cur = conn.cursor()
    if language == 'eng':
        cur.execute("SELECT shop_id, shop_name, shop_location, shop_address, shop_x, shop_y FROM fyp_shop where shop_flag = 1")
    else:
        cur.execute("SELECT shop_id, shop_name_zh, shop_location, shop_address_zh, shop_x, shop_y FROM fyp_shop where shop_flag = 1")
    result = cur.fetchall()
    cur.execute('commit')

    for i in result:
        if i[2] == 'h':
            x = i[4]
            y = i[5]
            break

    return render_template('index.html', result=result, x=x, y=y)

@app.route("/login", methods=['POST', 'GET'])
def login():
    session['path'] = 'login'
    if request.method == "POST":
        session.pop = ('username', None)
        session.pop = ('userid', None)
        username = request.form["username"]
        pw = request.form['password']
        if username:
            if pw:
                try:
                    cur = conn.cursor()
                    cur.execute("SELECT * FROM fyp_user WHERE user_username= '%s'" % (username))
                    result = cur.fetchone()
                    cur.execute('commit')
                    password = result[4]

                    if pw == password:
                        session['userid'] = result[0]
                        session['username'] = result[1]
                        flash(gettext('Login Success'), 'success')
                        return redirect(url_for('index'))
                    else:
                        flash(gettext('Username or Password Incorrect'), 'danger')
                        return redirect(url_for('login'))
                except Exception as e:
                    print(e)
                    flash(gettext('Connection Error'), 'danger')
                    return redirect(url_for('login'))
            else:
                flash(gettext('Please input correct details'), 'danger')
                return redirect(url_for('login'))
        else:
            flash(gettext('Please input correct details'), 'danger')
            return redirect(url_for('login'))
    else:
        return render_template('login.html')

@app.route("/register", methods=['POST', 'GET'])
def register():
    session['path'] = 'register'
    if request.method == "POST":
        username = request.form["username"]
        email = request.form["email"]
        phone = request.form["phone"]
        pw = request.form['password']
        pw2 = request.form['password2']
        try:
            cur = conn.cursor()
            cur.execute("SELECT * FROM fyp_user WHERE user_username= '%s'" % (username))
            result = cur.fetchone()
            cur.execute('commit')

            if result:
                flash(gettext('Username has been used'), 'danger')
                return redirect(url_for('register'))

            else:
                cur = conn.cursor()
                cur.execute("SELECT * FROM fyp_user WHERE user_email= '%s'" % (email))
                result = cur.fetchone()
                cur.execute('commit')

                if result:
                    flash(gettext('Email has been used'), 'danger')
                    return redirect(url_for('register'))
                else:
                    cur = conn.cursor()
                    cur.execute("SELECT * FROM fyp_user WHERE user_phone= '%s'" % (phone))
                    result = cur.fetchone()
                    cur.execute('commit')

                    if result:
                        flash(gettext('Phone has been used'), 'danger')
                        return redirect(url_for('register'))
                    else:
                        if(pw == pw2):
                            try:
                                cur = conn.cursor()
                                rows = [(username,email,phone,pw)]
                                cur.bindarraysize = 1
                                cur.executemany("insert into fyp_user values (userid_increase.nextval,:1, :2, :3, :4)", rows)
                                conn.commit()
                                
                                flash(gettext('Success, you may login now!'), 'success')
                                return redirect(url_for('login'))

                            except Exception as e: 
                                print(e)
                                flash(gettext('Connection Error'), 'danger')
                                return redirect(url_for('register'))
 
                        else:
                            flash(gettext('Retype password incorrect'), 'danger')
                            return redirect(url_for('register'))                        
        except Exception as e:
            print(e)
            flash(gettext('Connection Error'), 'danger')
            return redirect(url_for('register'))
    else:
        return render_template('register.html')

@app.route("/logout")
def logout():
    path = session['path']
    session.clear()
    return redirect(url_for(path))

@app.route("/userpage")
def userPage():
    session['path'] = 'userPage'
    return 'userPage'

@app.route("/forgotpw", methods=['POST', 'GET'])
def forgotPw():
    session['path'] = 'forgotPw'
    if request.method == "POST":
        username = request.form["username"]
        email = request.form['email']
        try:
            cur = conn.cursor()
            cur.execute("SELECT user_email FROM fyp_user WHERE user_username= '%s'" % (username))
            result = cur.fetchone()
            cur.execute('commit')

            if result:
                email2 = result[0]
                if email == email2:
                    emailS = email
                    usernameS = username
                    now = datetime.datetime.now()
                    now_time = now.strftime("%y-%m-%d %H:%M:%S")

                    #readly Email
                    title = gettext('Forgot Password')
                    msg = Message(title, recipients=[email])
                    #Generate Verification code
                    new_pass = ''.join(random.choices(string.ascii_uppercase + string.digits, k=5))
                    global code
                    code = new_pass

                    #body of the email
                    engVer = '''
    Dear user : {user} ,
    
    This is a Verification code for your account for verificate on the action of forgot password.
    If you made this request, then please enter the Verification code now, do not share the code to other people.
    Verification code : {code}
    If you do not operate this action, please change the password now.

    From Admin
    Time sent : {time}
                    '''.format(user=usernameS, code=new_pass, time=now_time)

                    zhVer = '''
    尊敬的用戶 {user} :

    這是您帳戶的驗證碼，用於驗證忘記密碼的操作。
    如果您提出此請求，請立即輸入驗證碼，請勿與他人共享驗證碼。
    驗證碼：{code}
    如果不是您執行此操作，請立即更改密碼。

    從管理員
    發送時間：{time}
                    '''.format(user=usernameS, code=new_pass, time=now_time)

                    if language == 'eng':
                        msg.body = (engVer)
                    else:
                        msg.body = (zhVer)
                    try:
                        mail.send(msg)
                        session['resetPW'] = 'true'
                        session['resetPWUsername'] = username
                        session['resetPWEmail'] = email
                        return redirect(url_for('resetPw'))
                    except Exception as e:
                        print(e)
                        flash(gettext('Connection Error to Email server'), 'danger')
                        return redirect(url_for('forgotPw'))
                else:
                    flash(gettext('Email Incorrect'), 'danger')
                    return redirect(url_for('forgotPw'))
            else:
                flash(gettext('Username Incorrect'), 'danger')
                return redirect(url_for('forgotPw'))
        except Exception as e:
            print(e)
            flash(gettext('Connection Error'), 'danger')
            return redirect(url_for('forgotPw'))
    else:
        return render_template('forgotpw.html')

@app.route("/resetpw", methods=['POST', 'GET'])
def resetPw():
    session['path'] = 'resetPw'
    if request.method == "POST":
        codeFromUser = request.form["code"]
        if code == codeFromUser:
            return redirect(url_for('changepwR'))
        else:
            flash(gettext('Verification code incorrect'), 'danger')
            return redirect(url_for('resetPw'))
    else:
        try:
            if session['resetPW'] == 'true':
                return render_template('resetpw.html')
            else:
                return redirect(url_for('login'))
        except Exception as e:
            print(e)
            return redirect(url_for('login'))

@app.route("/changepwR", methods=['POST', 'GET'])
def changepwR():
    session['path'] = 'changepwR'
    if request.method == "POST":
        pw1 = request.form["password1"]
        pw2 = request.form["password2"]
        user = session['resetPWUsername']
        if pw1 == pw2:
            try:
                cur = conn.cursor()
                sql = "update fyp_user set user_password = '" + str(pw2) + "' where user_username = '" + str(user) + "'"
                cur.execute(sql)
                conn.commit()
                session.clear()
                flash(gettext('Success to change password'), 'success')
                return redirect(url_for('login'))
            except Exception as e:
                print(e)
                flash(gettext('Connection Error'), 'danger')
                return redirect(url_for('changepwR'))
        else:
            flash(gettext('Retype password incorrect'), 'danger')
            return redirect(url_for('changepwR'))
    else:
        return render_template('changepwR.html')

@app.route("/redirecting", methods=['POST', 'GET'])
def redirecting():
    if request.method == "POST":
        button = request.form['btn']
        shop = request.form['shops']
        
        if button == 'sr':
            return redirect(url_for('seatReservation', shop=shop))
        elif button == 'qr':
            return redirect(url_for('qrCode'))
        else:
            return redirect(url_for('takeOut', shop=shop))
    else:
        return redirect(url_for('index'))

@app.route("/seatReservation")
def seatReservation():
    session['path'] = 'seatReservation'
    shop=request.args.get('shop')
    return render_template('seatReservation.html')

@app.route("/qrCode")
def qrCode():
    session['path'] = 'qrCode'
    return render_template('qrCode.html')

@app.route("/takeOut")
def takeOut():
    session['path'] = 'takeOut'
    shop=request.args.get('shop')
    return render_template('takeOut.html')

if __name__ == "__main__":
    app.run(debug=True)
