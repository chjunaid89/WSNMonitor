volatile int bin_data[39];
int Rx = 2;
volatile bool flag;
volatile int m = 0;
volatile int bin_data_ten[10];
volatile int remainder;
volatile int decimal = 0;
volatile int base = 1;
volatile char Rx_char;
volatile int n = 0;



const int TX_PIN = 5;                                          // Output Pin
float baud_rate = 1200;                                        // Baud rate
float time_period = 1.0/baud_rate;                             // Time period
float delay_1 = time_period/2.0;                               // Half of the time period
float delay_2 = (delay_1*1000000.0)-52.0;                      // Time for delayMicrosecond

char msg[] = "UHello WorldU";                             // Message to be transmiited

int transmit_msg;
const byte msg_byte = 7;
int i,j,k,l;
unsigned long c = 0;
unsigned long c1 = 0;

// Method that runs only once, used for initialization.
void setup() {
  flag = LOW;
  pinMode(Rx, INPUT);
  attachInterrupt(0, ascii, RISING);


  
  pinMode(TX_PIN, OUTPUT);                                    // Initializing the Pin as an Output
  Serial.begin(9600);                                         // Initialing the Serial monitior
}

// Method for Pulse With Modulation
// On-time or pulse width modulated by repeating on-off pattern 4 times
void pwm() {                                                  
  for (l = 0; l <= 3; l++){ 
    delayMicroseconds(52);
    digitalWrite(TX_PIN, HIGH);
    delayMicroseconds(52);
    digitalWrite(TX_PIN, LOW);
  }
}

// Method that runs repeatedly
void loop() {
  
  // Sending ten 0´s at the start and then the output message using Manchester coding.
  // Manchester coding:
  // 0 is 10 and 1 is 01.
  for (k = 0; k <= 9; k++){ 
    pwm();                                                   // Sending 1 using PWM method
    delayMicroseconds(delay_2);                              
    digitalWrite(TX_PIN, LOW);                               // Sending 0 after particular delay with respect to the time period
  }
  
  // Sending message to the output pin
  for (i = 0; i < sizeof(msg)-1; i++){    
    transmit_msg = msg[i];
    
//    Serial.write(transmit_msg);
    
    for (j = 0; j < msg_byte; j++) {  
      byte data = bitRead(transmit_msg, j);                  // Reading binary code of the message bit by bit
      if(data == 0){                                         // If bit is 0, send 10
        pwm();                                               // Sending 1 using PWM method
        delayMicroseconds(delay_2);
        digitalWrite(TX_PIN, LOW);                           // Sending 0 after particular delay with respect to the time period
      }
        else{                                                // If bit is 1, send 01
          delayMicroseconds(delay_2);
          digitalWrite(TX_PIN, LOW);                         // Sending 0 after particular delay with respect to the time period
          pwm();                                             // Sending 1 using PWM method

        }  
    }
    
  }
}





void ascii() {
  flag = HIGH;

  for (n = 0; n <= 39; n++) {
    bin_data[n] = digitalRead(Rx);
    delayMicroseconds(250);
  }

  for (n = 3; n <= 39; n++) {
    bin_data_ten[m] = bin_data[n];
    m++;
    n=n+3;
  }

//   for(int n=0; n<=9; n++){
//     Serial.println(bin_data_ten[n]);
//    }
//
  for(int n=0; n<=6; n++){
  remainder = bin_data_ten[n+1] % 10;
  decimal += remainder * base;
  bin_data_ten[n+1] /= 10;
  base = base * 2;
  }
  
  Rx_char = decimal;
  Serial.print(Rx_char);
  
  base=1 ;
  decimal=0;
  m = 0;

}
      

