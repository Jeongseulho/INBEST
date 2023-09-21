import { useFearRadialBar } from "./useFearRadialBar";

interface Props {
  percentage: number;
}

const FearRadialBar = ({ percentage }: Props) => {
  const { rotation, displayedPercentage } = useFearRadialBar(percentage);

  return (
    <div className=" relative shadow-component p-4">
      <h4 className=" mb-4">공포, 탐욕 지수</h4>
      <div
        className="absolute bg-black w-3 h-3 rounded-full z-10"
        style={{
          bottom: "18%",
          left: "50%",
          transform: `translateX(-50%) rotate(${rotation}deg) translateY(90px)`,
          transformOrigin: "center bottom",
          transition: "transform 1s ease-in-out",
        }}
      ></div>

      <svg width="177" height="89" viewBox="0 0 177 89" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path
          d="M173.262 88C174.967 88 176.354 86.6173 176.294 84.9135C175.747 69.3495 171.069 54.2074 162.739 41.0369C161.829 39.5992 159.909 39.2385 158.502 40.1946V40.1946C157.091 41.1536 156.731 43.073 157.639 44.5176C165.272 56.6598 169.575 70.5911 170.116 84.9137C170.181 86.6174 171.557 88 173.262 88V88Z"
          fill="#16C784"
        ></path>
        <path
          d="M156.897 37.9461C158.276 36.9419 158.582 35.0049 157.527 33.664C147.855 21.3698 135.074 11.8665 120.501 6.13374C118.919 5.51151 117.158 6.35475 116.589 7.9564V7.9564C116.019 9.56129 116.861 11.3189 118.444 11.9467C131.848 17.2616 143.611 26.0103 152.545 37.3081C153.6 38.6424 155.522 38.9473 156.897 37.9461V37.9461Z"
          fill="#93D900"
        ></path>
        <path
          d="M113.03 6.79368C113.529 5.1655 112.613 3.4359 110.968 2.99624C95.9311 -1.02229 80.0941 -0.998203 65.0696 3.06605C63.4258 3.51072 62.5154 5.24312 63.0192 6.86979V6.86979C63.5221 8.49352 65.2439 9.3971 66.8859 8.95738C80.732 5.24947 95.3122 5.22729 109.17 8.89306C110.813 9.32777 112.532 8.41894 113.03 6.79368V6.79368Z"
          fill="#F3D42F"
        ></path>
        <path
          d="M60.1455 7.80322C59.5852 6.19848 57.8286 5.34566 56.2435 5.95929C41.7314 11.5772 28.9667 20.9284 19.2489 33.0606C18.1823 34.3922 18.4716 36.3318 19.842 37.3481V37.3481C21.2083 38.3612 23.1323 38.0731 24.1989 36.7482C33.1738 25.6002 44.9213 16.992 58.2681 11.7833C59.8547 11.1641 60.707 9.41116 60.1455 7.80322V7.80322Z"
          fill="#EA8C00"
        ></path>
        <path
          d="M17.7126 40.3161C16.3039 39.3625 14.3841 39.7265 13.4772 41.1658C5.1935 54.313 0.544063 69.4152 0.00210452 84.935C-0.0573952 86.6388 1.3307 88.0212 3.03561 88.0208V88.0208C4.74051 88.0203 6.11617 86.6374 6.18018 84.9337C6.71672 70.6522 10.9931 56.7581 18.5827 44.6376C19.4882 43.1915 19.1255 41.2726 17.7126 40.3161V40.3161Z"
          fill="#EA3943"
        ></path>
      </svg>
      <div className="  absolute left-1/2 -translate-x-1/2 bottom-7 flex flex-col items-center">
        <p className=" text-xl font-medium">{displayedPercentage}</p>
        <p className=" text-md font-medium text-myGray">중간</p>
      </div>
    </div>
  );
};
export default FearRadialBar;
