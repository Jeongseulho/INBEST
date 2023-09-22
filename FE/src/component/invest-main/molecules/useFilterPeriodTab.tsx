// import { useState } from "react";
// import { MIN_PERIOD, MAX_PERIOD } from "../../../constant/FILTER_MIN_MAX";
// import { Period } from "../../../type/GroupFilter";

// export const useFilterPeriodTab = (period: Period, dispatch: React.Dispatch<{ type: "PERIOD"; payload: Period }>) => {
//   const [prevPeriod, setPrevPeriod] = useState<number[]>([MIN_PERIOD, MAX_PERIOD]);
//   const onChangeCheckboxChange = (e: React.ChangeEvent<HTMLInputElement>) => {
//     if (e.target.checked) {
//       setPrevPeriod(period as number[]);
//       dispatch({ type: "PERIOD", payload: "boostMode" });
//     } else {
//       dispatch({ type: "PERIOD", payload: prevPeriod });
//     }
//   };

//   return {
//     onChangeCheckboxChange,
//     prevPeriod,
//   };
// };
