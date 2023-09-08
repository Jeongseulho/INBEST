import ReactDatePicker from "react-datepicker";
import { useInputDatePicker } from "./useInputDatePicker";
import "react-datepicker/dist/react-datepicker.css";
import { getMonth, getYear, format } from "date-fns";
import { AiFillCaretLeft, AiFillCaretRight, AiFillCalendar } from "react-icons/ai";
import { ko } from "date-fns/locale";
import { Controller, Control } from "react-hook-form";
import { SignupFormValue } from "../../../type/SignupForm";

const InputDatePicker = ({ control }: { control: Control<SignupFormValue> }) => {
  const { selectedDate, setSelectedDate, month, handleMonthChange, years, months } = useInputDatePicker();

  return (
    <div className=" relative">
      <Controller
        name="birth"
        control={control}
        render={({ field: { onChange } }) => (
          <ReactDatePicker
            onChange={(date) => {
              setSelectedDate(date);
              onChange(format(date, "yyyy-MM-dd"));
            }}
            selected={selectedDate}
            dateFormat={"yyyy-MM-dd"}
            maxDate={new Date()}
            locale={ko}
            fixedHeight
            placeholderText="생일을 선택해 주세요"
            className="signup-input w-full"
            onMonthChange={handleMonthChange}
            dayClassName={(d) =>
              d.getDate() === selectedDate?.getDate() ? "selected-day" : d.getMonth() === month ? "" : "gray-day"
            }
            renderCustomHeader={({
              date,
              changeYear,
              changeMonth,
              decreaseMonth,
              increaseMonth,
              prevMonthButtonDisabled,
              nextMonthButtonDisabled,
            }) => (
              <div className="bg-white h-8 flex justify-center">
                <button className="me-3" onClick={decreaseMonth} disabled={prevMonthButtonDisabled}>
                  <AiFillCaretLeft size="20" />
                </button>
                <select
                  className="text-base"
                  value={getYear(date)}
                  onChange={({ target: { value } }) => changeYear(Number(value))}
                >
                  {years.map((option) => (
                    <option key={option} value={option}>
                      {option}년
                    </option>
                  ))}
                </select>

                <select
                  className="text-base"
                  value={months[getMonth(date)]}
                  onChange={({ target: { value } }) => changeMonth(months.indexOf(value))}
                >
                  {months.map((option) => (
                    <option key={option} value={option}>
                      {option}
                    </option>
                  ))}
                </select>

                <button className="ms-3" onClick={increaseMonth} disabled={nextMonthButtonDisabled}>
                  <AiFillCaretRight size="20" />
                </button>
              </div>
            )}
          />
        )}
      />

      <div className=" absolute right-2 top-1/2 transform -translate-y-1/2">
        <AiFillCalendar />
      </div>
    </div>
  );
};

export default InputDatePicker;
